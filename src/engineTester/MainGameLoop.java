package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		 DisplayManager.createDisplay();
		 Loader loader = new Loader();

		 RawModel model = OBJLoader.loadObjModel("tree", loader);
		 RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
		 RawModel fernModel = OBJLoader.loadObjModel("fern", loader);

		 TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		 TexturedModel grass = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
		 TexturedModel fern = new TexturedModel(fernModel,new ModelTexture(loader.loadTexture("fern")));

		 
		 grass.getTexture().setUseFakeLighting(true);
		 grass.getTexture().setHasTransparacy(true);
		 fern.getTexture().setHasTransparacy(true);
		 fern.getTexture().setUseFakeLighting(true);
		 
		 List<Entity> entities = new ArrayList<Entity>();
		 
		 Random random = new Random();
		 for(int i=0;i<500;i++){
			 entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,
					 random.nextFloat() * -600),0,0,0,3));
			 
			 entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0,
					 random.nextFloat() * -600) ,0 ,0 ,0, 1));
			 
			 entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0,
					 random.nextFloat() * -600) ,0 ,0 ,0, 0.6f));
		 }

		 Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

		 Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
		 Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));

		 Camera camera = new Camera();   

		 MasterRenderer renderer = new MasterRenderer();

		while (!Display.isCloseRequested()) {
			// game logic
			camera.move();
			
			// Prepara o render
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			
			renderer.render(light, camera);

			// update a tela
			DisplayManager.updateDisplay();
		}	

		// Libera memoria corretamente
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
// This line make it work