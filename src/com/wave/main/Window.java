package com.wave.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7091006469336366864L;

	public Window (int width, int height, String title, Game game) {
		
		//Window frame
		JFrame frame = new JFrame(title);
		
		//Set preferred(default?), max, and min size of the frame
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		//Stops the thread on the game when game its closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//locks window resize
		frame.setResizable(false);;
		
		//so windows is set to initialize in the center of the screen rather than in top left(default)
		frame.setLocationRelativeTo(null);
		
		//adding game class into the game
		frame.add(game);
		
		//set the frame to visible (shouldn't this be default?)
		frame.setVisible(true);
		game.start();
	}
	
}
