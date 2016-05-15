package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;

	// Funcao que Constroi Display	
	public static void createDisplay () {

		ContextAttribs attribs = new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attribs);

			// Nome da Tela
			Display.setTitle("JesusZombie");

		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Tamanho da janela utilizando OPENGL
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}

	// Funcao que da update na tela
	public static void updateDisplay () {
		Display.sync(FPS_CAP);
		Display.update();
	}

	// Mata o Display	
	public static void closeDisplay () {
		Display.destroy();
	}

}
//This line make it work