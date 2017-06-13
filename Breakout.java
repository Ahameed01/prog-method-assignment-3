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

/** Random number generator for vx */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
/** Time delay between ball movement */
	private static final int DELAY = 25;
	
/** Total number of bricks */
	private int brickCounter = 100;
	
 /**Velocity of the ball */
	private double vx, vy;
	
	private GRect rect;
	
	private GRect paddle;
	
	private GOval ball;
/* Method: run() */

	/** Runs the Breakout program. */
	public void run() {
		
		/* This method can be described as the complete overview of everything that is 
		 * expected of this version of the breakout game.The main activities are
		 * designing the appearance of the game, specifying the maximum number of turns
		 * and playing the game.*/
		
		
		for (int n = 0; n < NTURNS; n++){
			GImage image = new GImage("background.jpg");
			image.scale(0.8, 1.15);
			add(image, 0, 0);
			designTheGame();
			playTheGame();
			if (brickCounter == 0){
			ball.setVisible(false);
			printWinMessage();
			break;
			}
			if (brickCounter > 0){
				removeAll();
			}
		}
		if (brickCounter > 0){
			printLossMessage();
		}
		
	}
		/* This part designs the outline of the game.The size of the game window,
		 * the bricks, paddle and ball*/
		private void designTheGame(){
		setSize (WIDTH, HEIGHT);
		drawBricks(0, BRICK_Y_OFFSET);
		drawPaddle();
		drawBall();
	
		}
		/* This part draws the bricks, arranges them and assigns the 
		 * various colours to the bricks. */
		private void drawBricks(double brcx, double brcy){
		for (int row = 0; row < NBRICK_ROWS; row++){
 			for (int column = 0; column < NBRICKS_PER_ROW; column++){
 				double y = brcy + (BRICK_HEIGHT +BRICK_SEP) * row;
 		 		double x = brcx + (BRICK_WIDTH + BRICK_SEP) *column;
 		 		rect = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
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
 		/* This method designs the paddle and positions it in the middle of the canvas
 		 * as its initial position. it is set to react to movement of the mouse. */
 		private void drawPaddle(){
 			double x = getWidth()/2 - PADDLE_WIDTH/2;
 			double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
 			paddle = new GRect (x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
 			paddle.setFilled(true);
 			paddle.setColor(Color.BLACK);
 			add (paddle);
 			addMouseListeners();
 		}
 		
 		/* This method is called whenever an event is being listened for in the program
 		 * which in this case is the movement of the mouse in a specified range within
 		 * the game window. */
 		public void mouseMoved(MouseEvent e){
 			double x = e.getX();
 			if ((e.getX() < getWidth() - PADDLE_WIDTH/2) && (e.getX() > PADDLE_WIDTH/2)) {
 				paddle.setLocation(e.getX() - PADDLE_WIDTH/2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
 			}

 		}
 		
 		/* The ball is designed in this method*/
 		private void drawBall(){
 		 ball = new GOval (WIDTH/2 - BALL_RADIUS, HEIGHT/2 - BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
 		 ball.setFilled(true);
 		 ball.setColor(Color.BLACK);
 		 add (ball);
 		
 		 
 	 }
 		/* This method designs all the aspects of what is needed to play the game. */
 		private void playTheGame(){
 			waitForClick();
 			getBallVelocity();
 			while (true){
 				moveBall();
 				if (ball.getY() >= getHeight()){
 					break;
 				}
 				if (brickCounter == 0){
 					break;
 				}
 			}
 		}
 	
 		/* This method generates the velocity of the ball around the game window. */
 		private void getBallVelocity(){
 			vy = 4.0;
 			vx = rgen.nextDouble(1.0, 3.0);
 			if (rgen.nextBoolean(0.5)){
 				vx = -vx;
 			}
 		}
 		
 		/* The ball's movement around the game window is designed here, including
 		 * collision with the walls, bricks and paddle and falling off the sides
 		 * of the paddle which signifies losing the game. */
 		private void moveBall(){
 			ball.move(vx,  vy);
 			
 		//To check for collision with the walls on both sides.
 			if ((ball.getX() - vx <= 0 && vx < 0) || (ball.getX() + vx >= (getWidth() - BALL_RADIUS * 2) && vx > 0)){
 			vx = -vx;
 			}
 		/*To check for collision at the top wall of the window, there's no need
 		 * checking for collision with the bottom wall because the will fall through
 		 * the side of the paddle and the player loses the game at that instant*/
 		if ((ball.getY() - vy <= 0 && vy < 0)){
 			vy = -vy;
 		}
 		//Checking for other objects, which are the paddle and bricks
 		GObject collider = getCollidingObject();
 		if (collider == paddle){
 			if (ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2 && ball.getY() < getHeight() -PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2 + 4){
 				vy = -vy;
 			}
 		}
 		else if (collider != null){
 			remove(collider);
 			brickCounter--;
 			vy = -vy;
 		}
 		pause(DELAY);
}
 		private GObject getCollidingObject(){
 			if ((getElementAt(ball.getX(), ball.getY())) != null){
 				return getElementAt(ball.getX(), ball.getY());
 			}
 			else if ((getElementAt((ball.getX() + BALL_RADIUS * 2), ball.getY()) != null)){
 				return (getElementAt((ball.getX() + BALL_RADIUS * 2), ball.getY()));
 			}
 			else if ((getElementAt((ball.getX() + BALL_RADIUS * 2), (ball.getY() + BALL_RADIUS * 2)) != null)){
 				return (getElementAt((ball.getX() + BALL_RADIUS * 2), ball.getY() + BALL_RADIUS * 2));
 			}
 			else if ((getElementAt((ball.getX()), (ball.getY() + BALL_RADIUS * 2)) != null)){
 				return (getElementAt((ball.getX()), ball.getY() + BALL_RADIUS * 2));
 			}
 			else{
 				return null;
 			}
 		}
 		
	/* */
 		private void printLossMessage(){
 			GLabel label = new GLabel(" GAME OVER - YOU LOST!!! ", getWidth() / 2, getHeight() / 2);
 			label.move(-label.getWidth()/2, -label.getHeight());
 			label.setColor(Color.BLUE);
 			add(label);
 			
 		}
 		
 		/* */
 		private void printWinMessage(){
 			GLabel label = new GLabel(" GAME COMPLETE - YOU WON!!! ", getWidth() / 2, getHeight() / 2);
 			label.move(-label.getWidth() / 2, label.getHeight());
 			label.setColor(Color.GREEN);
 			add(label);
 			
 		}
 		
}