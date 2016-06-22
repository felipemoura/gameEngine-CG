package entities;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gui.GuiTexture;

public class ColisionLife {
	
	public static void checkColision (Camera camera, List<Entity> lifes, List<GuiTexture> guis) {
		for (int i = 0; i < lifes.size(); i++) {
			if (Math.abs(lifes.get(i).getPosition().x - camera.getPosition().x) <= 1.5 &&
					Math.abs(lifes.get(i).getPosition().z - camera.getPosition().z) <= 1.5) {
				lifes.remove(i);
				
				GuiTexture guiaux = guis.get(1);
				Vector2f positionaux = guiaux.getPosition();
				Vector2f scaleaux = guiaux.getScale();
				
				if (scaleaux.x < 0.21f) {
					positionaux.setX(positionaux.x + 0.1f);
					scaleaux.setX(scaleaux.x + 0.1f);
					
				}
				guiaux.setPosition(positionaux);
				guiaux.setScale(scaleaux);
				guis.add(1, guiaux);
				
			}
		}
		
		
		

	}


}
