package entities;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	private Vector3f position = new Vector3f (-200,2,-200);
	private float pitch;
	private float yaw;
	private float roll;
	private float upwardSpeed = 0;
	private boolean isFlying = false;
	private ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	
    private static final float MOVEMENT_SPEED 		= 50.0f;
    private static final float MOUSE_SENSITIVITY	= 0.1f; 
    private static final float GRAVITY				= -50.0f;
    private static final float JUMP_POWER			= 30.0f;
    private static final float TERRAIN_HEIGHT		= 2.0f;
    
    public Camera () {
	    //esconde o  mouse
	    Mouse.setGrabbed(true);
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
        
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) // jump
        {
        	jump () ;
        }
        
        // Tiro
        if (Mouse.isButtonDown(0)) {
        	bullet.add(new Bullet ());
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
	
	private void jump () {
		// para nao voar infinito
		if (!isFlying ()) {
			upwardSpeed = JUMP_POWER;
			setFlying(true);
		}
	}

	public Vector3f getPosition() {
		return position;
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
}
