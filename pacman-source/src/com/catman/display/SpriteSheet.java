package com.catman.display;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private BufferedImage sheet;
	
	public SpriteSheet(String path) {
		try {
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			System.out.println("Failed to load image");
		}
	}
	
	public BufferedImage getSprite(int xx, int yy) {
		return sheet.getSubimage(xx, yy, 32, 32);
	}
	
}
