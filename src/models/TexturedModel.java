package models;

import textures.ModelTexture;

public class TexturedModel {

	private RawModel rawModal;
	private ModelTexture texture;
	
	public TexturedModel (RawModel model, ModelTexture texture) {
		this.rawModal = model;
		this.texture = texture;
	}

	public RawModel getRawModal() {
		return rawModal;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	
	
}
