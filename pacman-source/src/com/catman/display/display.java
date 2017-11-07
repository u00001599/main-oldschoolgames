package com.catman.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.catman.main.*;
import javax.imageio.ImageIO;

public class display {

	public int WIDTH;
	public int HEIGHT;
	
	public Tile[][] tiles;
	
	public List<com.catman.actors.food> catnip;
	public List<com.catman.actors.ghost> enemies;
	
	public display(String path) {
		catnip = new ArrayList<>();
		enemies = new ArrayList<>();
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			this.WIDTH = map.getWidth();
			this.HEIGHT = map.getHeight();
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[WIDTH][HEIGHT];
			map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
			
			
			for(int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					int val = pixels[xx + (yy * WIDTH)];
					
					if (val == 0xFF000000) {
						//Tile
						tiles[xx][yy] = new Tile(xx * 32, yy * 32);
					} else if(val == 0xFF55FF2B) {
						// Player Detection
						Game.player.x = xx*16;
						Game.player.y = yy*16;
					} else if(val == 0xFFFF8019) {
						// Enemy detection
						enemies.add(new com.catman.actors.ghost(xx * 32, yy * 32));
					} else {
						catnip.add(new com.catman.actors.food(xx * 32, yy * 32));
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		for (int i = 0; i < enemies.size(); i ++) {
			enemies.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if(tiles[x][y] != null) tiles[x][y].render(g);
				
			}
		}
		for (int i = 0; i < catnip.size(); i++) {
			catnip.get(i).render(g);
		}
		for (int i = 0; i < enemies.size(); i ++) {
			enemies.get(i).render(g);
		}
		
		
	}
}
