package com.catman.actors;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class food extends Rectangle {
	private static final long serialVersionUID = 1L;

	public food(int x, int y) {
		setBounds(x + 10, y + 8, 8, 8);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}
}
