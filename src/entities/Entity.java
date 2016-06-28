package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity {
	private TexturedModel model;
	protected Vector3f position;
	protected float rotX, rotY, rotZ;
	private float scale;

	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super();
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public int Update(){
		updateRotation();
		
		return 0;
	}
	
	protected void updateRotation(){
		Vector3f relation;
		
		if(Camera.getPosition().z < position.z){
			relation = new Vector3f(Camera.getPosition().x - position.x, 0, Camera.getPosition().z - position.z);
			relation = (Vector3f) relation.normalise();
			float ang = (float) Math.acos(relation.getX());
			ang = (float) (180*ang/3.14 + 90);
			setRotY(ang);
		}
		else{
			relation = new Vector3f(position.x - Camera.getPosition().x, 0, position.z - Camera.getPosition().z);
			relation = (Vector3f) relation.normalise();
			float ang = (float) Math.acos(relation.getX());
			ang = (float) (180*ang/3.14 + 90 + 180);
			setRotY(ang);
		}
	}

	public void increasePosition (float dx, float dy, float dz) {
		this.position.x +=dx;
		this.position.y +=dy;
		this.position.z +=dz;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX+=dx;
		this.rotY+=dy;
		this.rotZ+=dz;
	}

	// Getters e Setters
	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
