package com.wave.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -726038495289796084L;
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;	//16 by 9 ratio
	private Thread thread;
	private boolean running = false;	//true if the tread is running

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		new Window(WIDTH, HEIGHT, "Wave Game!", this);
	}
	
	public synchronized void start() {
		//initializing thread
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			//stops the thread
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Game Loop
	 * Basically gets the current time, and waits for the game to "tick"
	 * The delta gets the difference between the now and lastTime variable
	 */
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		
		//ns is how many nanoseconds should pass per tick
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		//records the time in milliseconds
		long timer = System.currentTimeMillis();
		
		//frames will record how many frames pass per second
		int frames = 0;
		while(running) {
			
			//records the time in nanoseconds
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			//creates 3 buffers within the game
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.green);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.dispose();
		bs.show();
	}
}
