

package com.catman.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import com.catman.actors.*;
import com.catman.display.*;
/**
 *  Created by Marques Scripps and Anastasija Liepina
 */



import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;

	private boolean isRunning = false;
	public static final int  WIDTH = 840;
	public static final int HEIGHT = 480;
	public static final String NAME = "Java Coding";
	public static final Double VERSION = 0.1;
	public static String pather = "/maps/map-1.png";
	
	private Thread thread;
	
	public static pacman player;
	public static display display;
	public static SpriteSheet spritesheet;
	public boolean isEnter = false;
	public static final int GAME_OVER = 0;
	public static final int GAME = 1;
	public static final int PAUSE_GAME = 2;
	public static final int START_GAME = 3;
	public static int STATE = -1;
	public static int SCORE = 0;
	private int time = 0;
	private int targetFrames = 40;
	private boolean showText = true;
	
	public static boolean left = true;
	public static boolean right = true;
	public static boolean down = true;
	public static boolean up = true;
	
	
	
	public Game() {
		Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);
		setPreferredSize(dimension);
		setMinimumSize(dimension);
		setMaximumSize(dimension);

		
		addKeyListener(this);
		STATE = START_GAME;
		spritesheet = new SpriteSheet("/sprites/spritesheet.png");
		
		new Texture();
	}
	
	public synchronized void start() {
		if (isRunning) {
			return;
		}
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!isRunning) {
			return;
		}
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void tick() {
		if(STATE == GAME) {
			player.tick();
			display.tick();				
			} else if(STATE == GAME_OVER) {
				time++;
				if(time == targetFrames) {
					time = 0;
					if(showText) {
						showText = false;
					} else {
						showText = true;
					}
				}
				if(isEnter) {
					isEnter = false;
					player = new pacman(Game.WIDTH, Game.HEIGHT);
					display = new com.catman.display.display(pather);
					STATE = GAME;
				}
			} else if(STATE == PAUSE_GAME) {
				time++;
				if(time == targetFrames) {
					time = 0;
					if(showText) {
						showText = false;
					} else {
						showText = true;
					}
				}
				if(isEnter) {
					isEnter = false;
					STATE = GAME;
				}
			}else if(STATE == START_GAME) {
				time++;
				if(time == targetFrames) {
					time = 0;
						showText = false;
					} else {
						showText = true;
					}
				}
				if(isEnter) {
					isEnter = false;
					player = new pacman(Game.WIDTH, Game.HEIGHT);
					display = new com.catman.display.display(pather);
					STATE = GAME;
				}
		} 

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);		
		if(STATE == GAME) {
			player.render(g);
			display.render(g);
			g.setColor(Color.white);
			g.setFont(new Font("pixeled", Font.BOLD, 12));
			g.drawString("SCORE:" + SCORE, getX()+10, getY()+20);
		} else if(STATE == GAME_OVER) {			
			g.setColor(Color.white);
			g.setFont(new Font("pixeled", Font.BOLD, 14));
			if(showText)g.setColor(Color.RED); g.drawString("GAME OVER!", getX() + 240, getY()+20 );
			g.setColor(Color.white);
			g.drawString("PRESS THE 'START BUTTON' TO TRY AGAIN.", getX() + 60, getY() + 180 );
		}else if(STATE == PAUSE_GAME) {		
				g.setColor(Color.white);
				g.setFont(new Font("pixeled", Font.BOLD, 14));
				if(showText)g.setColor(Color.RED); g.drawString("GAME PAUSED!",  getX()  + 240, getY()+20 );
				g.setColor(Color.white);
				g.drawString("PRESS THE 'START BUTTON' TO RESUME.", getX() + 60, getY() + 180 );
		}else if(STATE == START_GAME) {		
				g.setFont(new Font("pixeled", Font.BOLD, 14));
				g.setColor(Color.white);
				g.drawString("PRESS THE 'START BUTTON' TO START!", getX() + 60, getY() + 180);
				g.setColor(Color.blue);
				if(showText)g.setColor(Color.blue);g.drawString("CATMAN!", getX() + 240, getY() + 100);
				g.setFont(new Font("pixeled", Font.BOLD, 8));
				g.setColor(Color.green);
				if(showText)g.setColor(Color.green);g.drawString("Created by Marques and Anastasija", getX()+180 , getY()+120);
			}
			
		g.dispose();
		bs.show();
	}
	
	
	@Override
	public void run() {
		requestFocus();
		
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 60.0; 
		double delta = 0;
		double ns = 1e9 / targetTick;
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1) {
				tick();
				render();
				fps++;
				delta--;
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println(fps);
				fps = 0;
				timer+=1000;
			}
		}
		
	}
	
	public static void main (String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle(NAME + " " + VERSION);
		frame.add(game);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		game.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (STATE == GAME) {
		if (e.getKeyCode() == KeyEvent.VK_D) player.RIGHT = true;
		if (e.getKeyCode() == KeyEvent.VK_S) player.DOWN = true;
		if (e.getKeyCode() == KeyEvent.VK_W) player.UP = true;
		if (e.getKeyCode() == KeyEvent.VK_A) player.LEFT = true;
		if (e.getKeyCode() == KeyEvent.VK_E) STATE = PAUSE_GAME;
		} else if (STATE == Game.GAME_OVER) {
			if(e.getKeyCode() == KeyEvent.VK_E) {
				isEnter = true;
			}
		}else if (STATE == Game.PAUSE_GAME) {
			if(e.getKeyCode() == KeyEvent.VK_E) {
				isEnter = true;
			}
		}else if (STATE == Game.START_GAME) {
			if(e.getKeyCode() == KeyEvent.VK_E) {
				isEnter = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) player.RIGHT = false;
		if (e.getKeyCode() == KeyEvent.VK_A) player.LEFT = false;
		if (e.getKeyCode() == KeyEvent.VK_S) player.DOWN = false;
		if (e.getKeyCode() == KeyEvent.VK_W) player.UP = false;
	}

	
	
}
