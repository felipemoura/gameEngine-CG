package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		// Criacao de um display
		DisplayManager.createDisplay();
		
		// Inicializacao do Loader
		Loader loader = new Loader();

		ModelData data = OBJFileLoader.loadOBJ("tree");
		ModelData data2 = OBJFileLoader.loadOBJ("grassModel");
		ModelData data3 = OBJFileLoader.loadOBJ("fern");
		ModelData data4 = OBJFileLoader.loadOBJ("capsula");

		
		// Inicializacoa dos modelos
		RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), 
				data.getNormals(), data.getIndices());
		RawModel grassModel = loader.loadToVAO(data2.getVertices(), data2.getTextureCoords(), 
				data2.getNormals(), data2.getIndices());
		RawModel fernModel = loader.loadToVAO(data3.getVertices(), data3.getTextureCoords(), 
				data3.getNormals(), data3.getIndices());
		RawModel capsuleModel = loader.loadToVAO(data4.getVertices(), data4.getTextureCoords(), 
				data4.getNormals(), data4.getIndices());
		//		RawModel model = OBJLoader.loadObjModel("tree", loader);
		//		RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
		//		RawModel fernModel = OBJLoader.loadObjModel("fern", loader);ww

		// Inicializacao das texturas
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel fern = new TexturedModel(fernModel,new ModelTexture(loader.loadTexture("fern")));
		TexturedModel capsule = new TexturedModel(capsuleModel,new ModelTexture(loader.loadTexture("capsula")));

		// Inicializacao de atributos especiais para as texturas
		grass.getTexture().setUseFakeLighting(true);
		grass.getTexture().setHasTransparacy(true);
		fern.getTexture().setHasTransparacy(true);
		fern.getTexture().setUseFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();

		// Determinando posicoes randomicas para cada objeto inserido
		Random random = new Random();
		for(int i=0;i<500;i++){

			entities.add(new Entity(capsule, new Vector3f(random.nextFloat()*800 - 400,12,
					random.nextFloat() * -600),random.nextFloat()*180,random.nextFloat()*180,random.nextFloat()*180,3));

			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,
					random.nextFloat() * -600),0,0,0,3));

			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0,
					random.nextFloat() * -600) ,0 ,0 ,0, 1));

			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0,
					random.nextFloat() * -600) ,0 ,0 ,0, 0.6f));
		}

		// Inicializao do modelo de Luz
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

		// Inicializacao do terreno
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("floor")));
		Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("floor")));

		// Inicializacao da Camera do usuario (FPS)
		Camera camera = new Camera();   

		// Iniciando o render do jogo
		MasterRenderer renderer = new MasterRenderer();

		// Loop do Jogo
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			
			// game logic
			/* Falta Fazer*/
			
			camera.move();

			// Renderizacoes
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