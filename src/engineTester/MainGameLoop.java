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

		ModelData data = OBJFileLoader.loadOBJ("capsula");

		
		// Inicializacoa dos modelos
		RawModel capsuleModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), 
				data.getNormals(), data.getIndices());

		// Inicializacao das texturas
		TexturedModel capsule = new TexturedModel(capsuleModel,new ModelTexture(loader.loadTexture("capsula")));

		
		// Inicializacao de atributos especiais para as texturas
		capsule.getTexture().setUseFakeLighting(true);
		capsule.getTexture().setHasTransparacy(true);
		
		List<Entity> entities = new ArrayList<Entity>();

		// Determinando posicoes randomicas para cada objeto inserido
		Random random = new Random();
		for(int i=0;i<1000;i++){

			entities.add(new Entity(capsule, new Vector3f(random.nextFloat()*800 - 400,12,
					random.nextFloat() * -600),random.nextFloat()*180,random.nextFloat()*180,random.nextFloat()*180,3));
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