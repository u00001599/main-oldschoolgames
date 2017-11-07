package com.catman.display;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Texture {
	public static BufferedImage[] player;
	public static BufferedImage[] doge;
	public static BufferedImage[] player2;
	public BufferedImage spritesheet;
	public static String Spritesheet_location = "/sprites/spritesheet.png";
	public int RESOURCE_PACK = 1;

	public Texture() {
			Spritesheet_location = "/sprites/pacman.png";
		
		
		try {
			spritesheet = ImageIO.read(getClass().getResource(Spritesheet_location));
		} catch (IOException e) {
			e.printStackTrace();
		}
			player2 = new BufferedImage[2];
			player2[0] = getSprite(64,0);
			player2[1] = getSprite(96,0);
			
			doge = new BufferedImage[2];
			doge[0] = getSprite(32,32);
			doge[1] = getSprite(0,32);
			
			
			player = new BufferedImage[2];
			
			player[0] = getSprite(0,0);
			player[1] = getSprite(32,0);
		}
		
	
	public BufferedImage getSprite(int xx, int yy) {
		return spritesheet.getSubimage(xx, yy, 32, 32);
	}
	
}
