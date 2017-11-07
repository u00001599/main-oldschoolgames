package com.catman.actors;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.catman.display.Texture;
import com.catman.display.display;
import com.catman.main.Game;

public class ghost extends Rectangle{
	private static final long serialVersionUID = 1L;	
	private int random = 0, smart = 1, find_path = 2;	
	private int state = random;	
	private int right = 0, left = 1, up = 2, down = 3;	
	private int dir = -1;	
	public Random randomGen;	
	private int time = 0;	
	private int targetTime = 60*4;
	private int spd = 1;
	private int lastDir = -1;

	public ghost(int x, int y) {
		randomGen = new Random();
		setBounds(x, y, 32, 32);
		dir = randomGen.nextInt(4);
	}
	
	public void tick() {
		
		if (state == random) {
			
			if( dir == right) {
				if(canMove(x+spd,y)) {
					x+=spd;
				} else {
					dir = randomGen.nextInt(4);
				}
				
			} else if(dir == left) {
				if(canMove(x-spd,y)) {
					x-=spd;
				}else {
					dir = randomGen.nextInt(4);
				}
				
			} else if(dir == up) {
				if(canMove(x,y-spd)) {
					y-=spd;
				}else {
					dir = randomGen.nextInt(4);
				}
				
			} else if(dir == down) {
				if(canMove(x,y+spd)) {
					y+=spd;
				}else {
					dir = randomGen.nextInt(4);
				}
			}
			time++;
			
			if (time == targetTime)  {
				state = smart;
				time = 0;
			}
			
		} else if (state == smart) {
			
			//Follow the player??
			boolean move = false;
			
			if(x < Game.player.x) {
				if(canMove(x+spd,y)) {
					x+=spd;
					move = true;
					lastDir = right;
				}
			}
			if(x > Game.player.x) {
				if(canMove(x-spd,y)) {
					x-=spd;
					move = true;
					lastDir = left;
				}
			}
			if(y < Game.player.y) {
				if(canMove(x,y+spd)) {
					y+=spd;
					move = true;
					lastDir = down;
				}
			}
			if(y > Game.player.y) {
				if(canMove(x,y-spd)) {
					y-=spd;
					move = true;
					lastDir = up;
				}
			}
			
			
			if(x == Game.player.x && y == Game.player.y) move = true;
			
			if(!move) {
				state = find_path;
			}
			time++;
			
			if (time == targetTime)  {
				state = random;
				time = 0;
			}
			
			} else if(state == find_path) {
				if (lastDir == right) {
					
					if(y < Game.player.y) {
						if(canMove(x,y+spd)) {
							y+=spd;
							state = smart;
						}
					} else {
						if(canMove(x,y-spd)) {
							y-=spd;
							state = smart;
						}
					}
					
					if (canMove (x+spd,y)) {
						x+=spd;
					}
				} else if(lastDir == left) {
					
					if(y < Game.player.y) {
						if(canMove(x,y+spd)) {
							y+=spd;
							state = smart;
						}
					} else {
						if(canMove(x,y-spd)) {
							y-=spd;
							state = smart;
						}
					}
					
					if (canMove (x+spd,y)) {
						x+=spd;
					}
				} else if(lastDir == up) {
					
					if(x < Game.player.x) {
						if(canMove(x+spd, y)) {
							x+=spd;
							state = smart;
						}
					} else {
						if(canMove(x-spd,y)) {
							x-=spd;
							state = smart;
						}
					}
					
					if (canMove (x,y-spd)) {
						y-=spd;
					}
					
				} else if(lastDir == down) {
					
					if(x < Game.player.x) {
						if(canMove(x+spd, y)) {
							x+=spd;
							state = smart;
						}
					} else {
						if(canMove(x-spd,y)) {
							x-=spd;
							state = smart;
						}
					}
					
					if (canMove (x,y+spd)) {
						y+=spd;
					}
					
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
		if (lastDir == right) {
			g.drawImage(Texture.doge[com.catman.actors.pacman.cimageIndex%2], x , y , width, height, null);
		} else {
			g.drawImage(Texture.doge[com.catman.actors.pacman.cimageIndex%2], x+32 , y , -width, height, null);
		}
		

		
	}
	
}
