package com.wave.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Player extends GameObject{

	Random r = new Random();
	
	public Player(int x, int y, ID id) {
		super(x, y, id);
		
	}

	public void tick() {
		x += velX;
		y += velY;
		
		/*
		if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;*/
		x = Game.clamp(x, 0, Game.WIDTH - 36);
		y = Game.clamp(y, 0, Game.HEIGHT - 64);
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x,  y,  32,  32);
	}
	
	
	
}
