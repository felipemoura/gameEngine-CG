package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import gui.GuiRenderer;
import gui.GuiTexture;
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

	public static void main(String[] args) throws InterruptedException {

		// Criacao de um display
		DisplayManager.createDisplay();
		
		// Inicializacao do Loader
		Loader loader = new Loader();

		// Inicializa modelos de dados
		ModelData data = OBJFileLoader.loadOBJ("capsula");
		ModelData data2 = OBJFileLoader.loadOBJ("cubo");
		ModelData data3 = OBJFileLoader.loadOBJ("vida");

		// Inicializacoa dos modelos
		// Pega os
		//			vertices, coordenada uv da textura, normais e indices para VBO
		RawModel capsuleModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), 
				data.getNormals(), data.getIndices());
		
		RawModel cubeModel = loader.loadToVAO(data2.getVertices(), data2.getTextureCoords(), 
				data2.getNormals(), data2.getIndices());
		
		RawModel lifeModel = loader.loadToVAO(data3.getVertices(), data3.getTextureCoords(), 
				data3.getNormals(), data3.getIndices());
		
		// Inicializacao das texturas
		// .png em potencia de 2
		TexturedModel capsule = new TexturedModel(capsuleModel,new ModelTexture(loader.loadTexture("capsula")));
		TexturedModel cube = new TexturedModel(cubeModel,new ModelTexture(loader.loadTexture("cubo")));
		TexturedModel life = new TexturedModel(lifeModel,new ModelTexture(loader.loadTexture("vida")));
		
		
		// Inicializacao de atributos especiais para as texturas
		capsule.getTexture().setUseFakeLighting(true);
		capsule.getTexture().setHasTransparacy(true);
		
		List<Entity> entities = new ArrayList<Entity>();

		// Determinando posicoes randomicas para cada objeto inserido
		Random random = new Random();
		
		for(int i=0;i<100;i++){
			entities.add(new Entity(life, new Vector3f(random.nextFloat()*400 - 200,0,
					random.nextFloat() * -150),random.nextFloat()*90,random.nextFloat()*90,random.nextFloat()*90,3));
//			entities.add(new Entity(cube, new Vector3f(random.nextFloat()*800 - 400,10,
//					random.nextFloat() * -600),random.nextFloat()*180,random.nextFloat()*180,random.nextFloat()*180,3));
		}

		// Inicializao do modelo de Luz
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

		// Inicializacao do terreno
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("floor")));
//		Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("floor")));

		// Inicializacao da Camera do usuario (FPS)
		Camera camera = new Camera();   

		// Inicalizando GUI
		List<GuiTexture> guis = new ArrayList<GuiTexture> ();
		GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.65f,0.9f),new Vector2f(0.3f,0.3f));
//		GuiTexture gui2 = new GuiTexture(loader.loadTexture("green"), new Vector2f(-0.45f,0.9f),new Vector2f(0.1f,0.021f));
		GuiTexture gui2 = new GuiTexture(loader.loadTexture("green"), new Vector2f(-0.45f,0.9f),new Vector2f(0.35f,0.021f));
		GuiTexture gui3 = new GuiTexture(loader.loadTexture("youdied"), new Vector2f(0f,0f),new Vector2f(1f,1f));
		
		guis.add(gui);
		guis.add(gui2);
//		guis.add(gui4);
		
		GuiRenderer guiRenderer = new GuiRenderer (loader);
		
		// Iniciando o render do jogo
		MasterRenderer renderer = new MasterRenderer();

		// Loop do Jogo
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			
			// Game logic
			camera.move();

			// Renderizacoes
			renderer.processTerrain(terrain);
//			renderer.processTerrain(terrain2);

			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			
			GuiTexture guiaux = guis.get(1);
			Vector2f positionaux = guiaux.getPosition();
			Vector2f scaleaux = guiaux.getScale();
			positionaux.setX(positionaux.x - 0.001f);
			scaleaux.setX(scaleaux.x - 0.001f);
//			scaleaux.x -= 0.00000000000001f*DisplayManager.getFrameTimeSeconds();
			guiaux.setPosition(positionaux);
			guiaux.setScale(scaleaux);
			guis.add(1, guiaux);
			
			if (guiaux.getScale().getX() < 0) {

				guis.add(gui3);
			}
			
			// render do GUI
			guiRenderer.render(guis);
			
			
			// update a tela
			DisplayManager.updateDisplay();
		}	

		// Libera memoria corretamente
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
// This line make it work