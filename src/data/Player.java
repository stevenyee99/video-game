package data;
import static data.Level.*;
import static data.Boot.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import static helpers.LoadImages.*;
import static helpers.Clock.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;


public class Player {
	
	private static String direction;
	private static Texture tex;
	private static int playerX,playerY,speed, width, height, steps, holdingJump;
	private static int lastY,moveX = 0;
	private static LinkedList<Integer> lastYs;
	private static double gravity = .9;
	public static boolean gravityOn, jumping, jumpBoost;
	public static int yMove,changeY;
	
	
	public Player(Texture tex1, int x, int y, int width1, int height1) {

		tex = tex1;
		playerX = x;
		playerY = y;
		width = width1;
		height = height1;
		speed = 7;
		direction = "r";
		steps = 1;
		lastYs = new LinkedList<Integer>();
		gravityOn = true;
		for (int i = 0; i<10; i++) {
			lastYs.addFirst(i);
		}
	}



	public static void Update() {
		//Gravity();
		//MoveSpeed();	
		Jump();  //Removed  Maybe put back in?
		playerY += Level.yAdjust;
		playerX += Level.xAdjust;
		HitEdgeOfMap();
		DrawCharacter();
	}
	public static void CenterUpdate() {
		
		HitEdgeOfMap();
		DrawCharacter();
		//Jump();
		
	}
	
	

	public static void PlatformCheck() {
		if(Level.onPlatform == true) {
			//playerY = Level.platformsYCoord - height;
		}
		if(Level.leftPlatform == true  && Level.keysPressed[2] != 1) {
			playerX = Level.platformsXCoord - width;
		}
		if(Level.rightPlatform == true && Level.keysPressed[3] != 1) {
			playerX = Level.platformsRightXCoord;
		}
	}
	
	public static void MoveSpeed() {
		lastYs.addLast(playerY);
		lastY = playerY;
		moveX = (speed); // * (int) getDelta()*100);
		if(Level.keysPressed[3] == 1
				&& Level.playerCentered == false) {
			playerX += moveX; //* (int) helpers.Clock.d;
		}
		if (Level.keysPressed[2] == 1
				&& Level.playerCentered == false) {
			playerX -= moveX; //* (int) helpers.Clock.d;
		}
		/*
		if (Level.keysPressed[0] == 1) {
			playerY -= (speed * getDelta()*100);
		}
		if (Level.keysPressed[1] == 1) 
			playerY += (speed  * getDelta()*100);
		*/
	}
	
	public static void DrawCharacter() {
		if(Level.keysPressed[3] != 1 && Level.keysPressed[2] != 1)
			steps = 0;
		if(direction == "r" && onPlatform == false)
			DrawJumpRight(tex,playerX,playerY,width,height);
		else if(direction == "l" && onPlatform == false)
			DrawJump(tex,playerX,playerY,width,height);
		else if (direction == "r" && steps == 0) {
			DrawStandRight(tex,playerX,playerY,width,height);
			steps+=1;
		}
		else if (direction == "r" && steps >= 1 && steps <= 8) {
			DrawStep1Right(tex,playerX,playerY,width,height);
			steps+=1;
		}
		else if (direction == "r" && steps >= 9 && steps <= 16) {
			DrawStep2Right(tex,playerX,playerY,width,height);
			steps+=1;
		}
		else if (direction == "r" && steps >= 16) {
			DrawStep2Right(tex,playerX,playerY,width,height);
			steps = 1;
		}
		
		else if(direction == "l" && steps == 0)  {
			DrawStand(tex,playerX,playerY,width,height);
			steps +=1;
		}
		else if (direction == "l" && steps >= 1 && steps <= 8) {
			DrawStep1Left(tex,playerX,playerY,width,height);
			steps+=1;
		}
		else if (direction == "l" && steps >= 9 && steps <= 16) {
			DrawStep2Left(tex,playerX,playerY,width,height);
			steps+=1;
		}
		else if (direction == "l" && steps >= 16) {
			DrawStep2Left(tex,playerX,playerY,width,height);
			steps = 1;
		}
	}
	
	public static void HoldingJump() {
		System.out.println(holdingJump);
		if(jumping && Level.keysPressed[0] == 1) {
			holdingJump += (int) helpers.Clock.Delta();
			if(holdingJump > .5) {
				jumpBoost = true;
				holdingJump = 0;
			}
		}
		else {
			holdingJump = 0;
		}
	}
	public static void Jump() {
		//HoldingJump();
		if(jumping) {
			if(changeY > 0) {
				//Put back when I can jump again
				//if(jumpBoost) {
				//	changeY += 3;
				//	jumpBoost = false;
				//}
				//playerY -= changeY;
				Level.yMove += changeY;
				changeY -= gravity;
				Level.onPlatform = false;
			}
			else {
				jumping = false;
			}
		} else {
			changeY = 10;
			Level.yMove = 0;
		}
	}
	public static void Gravity() {
		if(Level.onPlatform == false) {
			playerY += 10;
		}
	}
	
	public static void HitEdgeOfMap() {
		if(playerX < 0)
			playerX = 0;
		if (playerX > WIDTH-width)
			playerX =  WIDTH-(int)width;
		if(playerY < 0)
			playerY = 0;
		if (playerY > HEIGHT-height)
			playerY = HEIGHT-(int)height;
	}
	


	
	private static void DrawStand(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(.1f,0);
		glVertex2f(width,0);
		glTexCoord2f(.1f,1);
		glVertex2f(width,height);
		glTexCoord2f(0,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	
	private static void DrawStandRight(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(.1f,0);
		glVertex2f(0,0);
		glTexCoord2f(0,0);
		glVertex2f(width,0);
		glTexCoord2f(0,1);
		glVertex2f(width,height);
		glTexCoord2f(.1f,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	private static void DrawStep1Right(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(.234f,0);
		glVertex2f(0,0);
		glTexCoord2f(.148f,0);
		glVertex2f(width,0);
		glTexCoord2f(.148f,1);
		glVertex2f(width,height);
		glTexCoord2f(.234f,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	private static void DrawStep1Left(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(.148f,0);
		glVertex2f(0,0);
		glTexCoord2f(.234f,0);
		glVertex2f(width,0);
		glTexCoord2f(.234f,1);
		glVertex2f(width,height);
		glTexCoord2f(.148f,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	private static void DrawStep2Right(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(.382f,0);
		glVertex2f(0,0);
		glTexCoord2f(.293f,0);
		glVertex2f(width,0);
		glTexCoord2f(.293f,1);
		glVertex2f(width,height);
		glTexCoord2f(.382f,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	private static void DrawStep2Left(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(.293f,0);
		glVertex2f(0,0);
		glTexCoord2f(.382f,0);
		glVertex2f(width,0);
		glTexCoord2f(.382f,1);
		glVertex2f(width,height);
		glTexCoord2f(.293f,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	
	private static void DrawJumpRight(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(.543f,0);
		glVertex2f(0,0);
		glTexCoord2f(.4375f,0);
		glVertex2f(width,0);
		glTexCoord2f(.4375f,1);
		glVertex2f(width,height);
		glTexCoord2f(.543f,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	private static void DrawJump(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(.4375f,0);
		glVertex2f(0,0);
		glTexCoord2f(.543f,0);
		glVertex2f(width,0);
		glTexCoord2f(.543f,1);
		glVertex2f(width,height);
		glTexCoord2f(.4375f,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}


	public void drawPlayer() {
		
	}
	public static int getPlayerX() {
		return playerX;
	}
	public static void setPlayerX(int playerX) {
		Player.playerX = playerX;
	}
	public static void setPlayerY(int playerY) {
		Player.playerY = playerY;
	}
	
	public static void setDirection(String way) {
		direction = way;
	}
	public static int getPlayerY() {
		return playerY;
	}
	public static int getWidth() {
		return width;
	}
	public static int getHeight() {
		return height;
	}
	public static int getMoveX() {
		return moveX;
	}



	
}