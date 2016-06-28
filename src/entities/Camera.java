package entities;

import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.peer.MouseInfoPeer;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.events.MouseEvent;

import renderEngine.DisplayManager;

public class Camera {
	private static Vector3f position = new Vector3f (-100,2,-100);
	private static boolean hitFlag; 
	private float pitch;
	private float yaw;
	private float roll;
	private float upwardSpeed = 0;
	private boolean isFlying = false;
	
    private static final float MOVEMENT_SPEED 		= 40.0f;
    private static final float MOUSE_SENSITIVITY	= 0.1f; 
    private static final float GRAVITY				= -50.0f;
    private static final float JUMP_POWER			= 30.0f;
    private static final float TERRAIN_HEIGHT		= 2.0f;
    
    private static int HP = 100;
    private static int killed = 0;
    
    public Camera () {
	    //esconde o  mouse
	    Mouse.setGrabbed(true);
	    hitFlag = false;
	}
    
    //moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance)
    {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw));
    }
     
    //moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance)
    {
        position.x += distance * (float)Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float)Math.cos(Math.toRadians(yaw));
    }
     
    //strafes the camera left relitive to its current rotation (yaw)
    public void strafeLeft(float distance)
    {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw-90));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw-90));
    }
     
    //strafes the camera right relitive to its current rotation (yaw)
    public void strafeRight(float distance)
    {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw+90));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw+90));
    }
	
	// move camera todo frame
	public void move () {
        if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN))//move forward
        {
            this.walkForward(MOVEMENT_SPEED *DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP))//move backwards
        {
            this.walkBackwards(MOVEMENT_SPEED *DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))//strafe left
        {
            this.strafeLeft(MOVEMENT_SPEED *DisplayManager.getFrameTimeSeconds());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))//strafe right
        {
            this.strafeRight(MOVEMENT_SPEED *DisplayManager.getFrameTimeSeconds());
        }
        
        checkBoundaries();
        
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) // jump
        {
        	jump ();
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_E)){
        	hitFlag = true;
        }
        else {
        	hitFlag = false;
        }
		
		// Move Camera para para cima ou baixo, direita ou esquerda
		yaw 	+= Mouse.getDX() * MOUSE_SENSITIVITY;
		pitch 	-= Mouse.getDY() * MOUSE_SENSITIVITY;
		
		// speed do jump
		upwardSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		getPosition().y += upwardSpeed * DisplayManager.getFrameTimeSeconds();
		
		if (getPosition().y < TERRAIN_HEIGHT) {
			upwardSpeed = 0;
			getPosition().y = TERRAIN_HEIGHT;
			setFlying(false);
		}
	}
	
	private void checkBoundaries(){
		if(position.x > 0)
			position.x = 0;
		else if(position.x < -200)
			position.x = -200;
		
		if(position.z > 0)
			position.z = 0;
		else if(position.z < -200)
			position.z = -200;
	}
	
	private void jump () {
		// para nao voar infinito
		if (!isFlying ()) {
			upwardSpeed = JUMP_POWER;
			setFlying(true);
		}
	}

	public static Vector3f getPosition() {
		return position;
	}
	
	public static void takeDamage(){
		HP -= 10;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}
	
	public static boolean getHitFlag(){
		return hitFlag;
	}
	
	public static int getHP(){
		return HP;
	}
	
	public static void addKill(){
		killed++;
	}
	
	public static int getKilled(){
		return killed;
	}

	public static void addHP(int i) {
		HP += i;
	}
}
