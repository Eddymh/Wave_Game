package com.wave.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -726038495289796084L;
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;	//16 by 9 ratio
	private Thread thread;
	private boolean running = false;	//true if the tread is running
	
	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		
		handler = new Handler();	//always on top to avoid random null pointer exception
		this.addKeyListener(new KeyInput(handler));
		
		new Window(WIDTH, HEIGHT, "Wave Game!", this);
		
		hud = new HUD();
		spawner = new Spawn(handler, hud);
		r = new Random();
		
		handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
		//handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy, handler));
		handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));

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
		this.requestFocus();
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
		handler.tick();
		hud.tick();
		spawner.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			//creates 3 buffers within the game
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		hud.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static int clamp(int var, int min, int max) {
		if(var >= max) return var = max;
		else if(var <= min) return var = min;
		else return var;
		
	}
	
}
