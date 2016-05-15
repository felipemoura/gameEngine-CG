package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {

	// Cria matriz de transformacao
	public static Matrix4f createTransformationMatrix (Vector3f translation, float rx, float ry,
			float rz, float scale) {

		Matrix4f matrix = new Matrix4f();

		// seta identidade primeiro
		matrix.setIdentity();

		// translacao
		Matrix4f.translate(translation, matrix, matrix);

		// rotacao em x
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);

		// rotacao em y
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);

		// rotacao em z
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);

		// escala
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);

		// retorna matriz de transformacao
		return matrix;
	}

	// cria matriz de view
	public static Matrix4f createViewMatrix(Camera camera) {
		
		Matrix4f viewMatrix = new Matrix4f();

		// seta identidade primeiro
		viewMatrix.setIdentity();
		
		Matrix4f.rotate(	(float) Math.toRadians(camera.getPitch()), 
							new Vector3f(1, 0, 0), 
							viewMatrix,
							viewMatrix);
		
		Matrix4f.rotate(	(float) Math.toRadians(camera.getYaw()), 
							new Vector3f(0, 1, 0), 
							viewMatrix, 
							viewMatrix);
		
		Vector3f cameraPos = camera.getPosition();
		
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		
		return viewMatrix;
	}

}
