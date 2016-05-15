package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f (0,2,0);
	private float pitch;
	private float yaw;
	private float roll;
    private float mouseSensitivity = 0.1f; 

    public Camera () {
	    //esconde o  mouse
	    Mouse.setGrabbed(true);
	}
	
	// move camera todo frame
	public void move () {
		
		// Move nas direcoes
		// UP
		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			position.z-=0.15f;
		}
		
		// DOWN
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			position.z+=0.15f;
		}
		
		// LEFT
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			position.x-=0.15f;
		}
		
		// RIGHT
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			position.x+=0.15f;
		}
		
		// Move Camera para para cima ou baixo, direita ou esquerda
		yaw 	+= Mouse.getDX() * mouseSensitivity;
		pitch 	-= Mouse.getDY() * mouseSensitivity;
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
}
