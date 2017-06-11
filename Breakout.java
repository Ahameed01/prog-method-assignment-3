/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		GImage image = new GImage("background.jpg");
		image.scale(0.8, 1.15);
		add(image, 0, 0);
		designTheGame();
		//playTheGame();
	}
		private void designTheGame(){
		setSize (WIDTH, HEIGHT);
		drawBricks(0, BRICK_Y_OFFSET);
		drawPaddle();
		drawBall();
	
		}
		private void drawBricks(double brcx, double brcy){
		for (int row = 0; row < NBRICK_ROWS; row++){
 			for (int column = 0; column < NBRICKS_PER_ROW; column++){
 				double y = brcy + (BRICK_HEIGHT +BRICK_SEP) * row;
 		 		double x = brcx + (BRICK_WIDTH + BRICK_SEP) *column;
 		 		GRect rect = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
 				rect.setFilled(true);
 
 		 		if (row < 2){
 		 			rect.setColor(Color.RED);
 		 		}
 		 		else if (row > 1 && row < 4){
 		 			rect.setColor(Color.ORANGE);
 		 		}
 		 		else if (row > 3 && row < 6){
 		 			rect.setColor(Color.YELLOW);
 		 		}
 		 		else if (row > 5 && row < 8){
 		 			rect.setColor(Color.GREEN);
 		 		}else {
 		 			rect.setColor(Color.CYAN);
 		 		}
 				add(rect);
 		 		}	
 		}
		}
 		
 		private void drawPaddle(){
 			double x = getWidth()/2 - PADDLE_WIDTH/2;
 			double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
 			GRect paddle = new GRect (x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
 			paddle.setFilled(true);
 			paddle.setColor(Color.MAGENTA);
 			add (paddle);
 			addMouseListeners();
 		}
 		public void mouseMoved(MouseEvent e){
 			double x = e.getX();
 			GRect paddle = new GRect (x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
			if( e.getX() >= 0 && e.getX() < (WIDTH - PADDLE_WIDTH)){
 				paddle.setLocation(e.getX(),getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
 			} else if (e.getX() > WIDTH){
 				paddle.setLocation(WIDTH - PADDLE_WIDTH, HEIGHT - PADDLE_Y_OFFSET);
 			}
 		}
 		
 	 private void drawBall(){
 		 GOval ball = new GOval (WIDTH/2 - BALL_RADIUS, HEIGHT/2 - BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
 		 ball.setFilled(true);
 		 ball.setColor(Color.WHITE);
 		 ball.setLocation((getWidth()/2), (getHeight() - PADDLE_Y_OFFSET - 20));
 		 add (ball);
 		addMouseListeners();
 		 
 	 }
 		
	//private void playTheGame();
	


}
