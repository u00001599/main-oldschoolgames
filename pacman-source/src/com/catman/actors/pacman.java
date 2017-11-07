package com.catman.actors;
import java.awt.Graphics;

import java.awt.Rectangle;
import java.util.Random;

import com.catman.display.Texture;
import com.catman.display.display;
import com.catman.main.Game;

public class pacman extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	public static int level = 1;
	
	public boolean RIGHT, LEFT, DOWN, UP;
	private int speed = 2;
	
	public static int ctime = 0, ctargetTime = 40;
	public static int cimageIndex = 0;
	
	private int lastDir = 1;
	
	public pacman(int x , int y) {
		setBounds(x, y, 32, 32);
	}
	
	public void tick() {
		if (RIGHT && canMove(x+speed,y)) {
			lastDir = 1;
			x+=speed;	
		}
		if (LEFT && canMove(x-speed,y)) {
			x-= speed;
			lastDir = -1;
		}
		if (UP && canMove(x, y-speed)) {
			y-= speed;
			lastDir  = 2;
		}
		if (DOWN && canMove(x, y+speed)) {
			y+= speed;
			lastDir = -2;
		}
		
		display Display = Game.display;
		
		for(int i = 0; i < Display.catnip.size(); i++) {
			
			if (this.intersects(Display.catnip.get(i))) {
				Display.catnip.remove(i);
				Random random = new Random();
				int num = random.nextInt(100);
				Game.SCORE += num;
				break;
			}
			
		}
		
		if (Display.catnip.size() == 0) {
			//Game end, you win!
			Game.player = new pacman(0,0);
			Game.display = new display("/maps/map-2.png");
			return;
		}
		for(int i = 0; i < Game.display.enemies.size(); i++) {
			ghost en = Game.display.enemies.get(i);
			if(en.intersects(this)) {
				Game.STATE = Game.GAME_OVER;
				Game.SCORE = 0;
			}
			
			ctime++;
			
			if(ctime == ctargetTime) {
				ctime = 0;
				cimageIndex++;
			}
		}
	}
	
	private boolean canMove(int nextx, int nexty) {
		
		Rectangle bounds = new Rectangle (nextx, nexty, width, height);
		display Display = Game.display;
		
		for (int xx = 0; xx < Display.tiles.length; xx++) {
			for (int yy = 0; yy < Display.tiles[0].length; yy++) {
				if(Display.tiles[xx][yy] != null) {
					if(bounds.intersects(Display.tiles[xx][yy])) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void render(Graphics g) {
		if(lastDir ==1) {
			g.drawImage(Texture.player[cimageIndex%2], x,y, width, height, null);
		} else if (lastDir == -1) {
		g.drawImage(Texture.player[cimageIndex%2], x+32 ,y, -width, height, null);
	} else if (lastDir == 2) {
		g.drawImage(Texture.player2[cimageIndex%2], x ,y+32, width, -height, null);
	} else {
		g.drawImage(Texture.player2[cimageIndex%2], x , y , width, height, null);
	}
	}
	
}
