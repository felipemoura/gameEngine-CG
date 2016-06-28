package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Enemy extends Entity{
	
	private final float MOVEMENT_SPEED = 5.0f;
	private int changeDirection, maxDirection;
	private float dx = 0, dz = 0;
	private int hitDistance = 20, attackDistance = 5;
	private double moveAngle;
	private int HP = 100;
	private boolean hitAux = false, attackAux = false, killedFlag = false;

	public Enemy(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		changeDirection = 0;
		maxDirection = (int) (100 + Math.random()*100); 
		moveAngle = Math.random()*6.28;
	}
	
	@Override
	public int Update(){
		if(killedFlag)
			return 0;
		
		changeDirection++;
		super.updateRotation();
		Move();
		Interact();
		if(HP <= 0){
			Camera.addKill();
			killedFlag = true;
		}
		
		return HP;
	}
	
	private void Interact(){
		double distance = playerDistance();
		if(distance < hitDistance && Camera.getHitFlag() && !hitAux){
			takeDamage();
			hitAux = true;
		}
		else if(!Camera.getHitFlag()){
			hitAux = false;
		}
		
		if(distance < attackDistance && !attackAux){
			Attack();
			attackAux = true;
		}
		else if (distance > attackDistance){
			attackAux = false;
		}
	}
	
	private void takeDamage(){
		HP -= 50;
	}
	
	private void Attack(){
		Camera.takeDamage();
	}
	
	private void Move(){
		if(changeDirection > maxDirection){
			changeDir(0);
		}
		else if(super.position.x + dx >= 0 || super.position.x + dx <= -200 ||
				super.position.z + dz >= 0 || super.position.z + dz <= -200){
			changeDir(1);
		}
		super.increasePosition(dx*DisplayManager.getFrameTimeSeconds(), 0, dz*DisplayManager.getFrameTimeSeconds());
	}
	
	private void changeDir(int type){
		if(type == 0){
			moveAngle += Math.random()*0.5 - 0.25;
		}
		else if(type == 1){
			moveAngle += Math.random()*0.5 - 3.14;
		}
		
		if(moveAngle > 6.28)
			moveAngle -= 6.28;
		
		dx = (float) (MOVEMENT_SPEED * Math.sin(moveAngle));
		dz = (float) (MOVEMENT_SPEED * Math.cos(moveAngle));
		
		changeDirection = 0;
	}
	
	private double playerDistance(){
		double distance;
		Vector3f dif = new Vector3f();
		
		dif.setX(super.position.x - Camera.getPosition().x);
		dif.setY(0);
		dif.setZ(super.position.z - Camera.getPosition().z);
		distance = dif.length();
		
		return distance;
	}
}
