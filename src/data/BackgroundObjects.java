package data;

import static helpers.Clock.getDelta;
import static helpers.LoadImages.DrawQuadTex;

import org.newdawn.slick.opengl.Texture;

public class BackgroundObjects {
	
	private Texture tex;
	private int x,y,width,height,xSpeed,ySpeed, startX, startY;
	private boolean respawn;
	private static float frameDelta;
	
	public BackgroundObjects(Texture tex,int x, int y, int width, int height,boolean respawn, String type) {
		this.tex = tex;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.startX = x;
		this.startY = y;
		this.respawn = respawn;
		if(type == "left") {
			this.ySpeed = 0 ;
			this.xSpeed = -2 ;
		}
		else {
			this.ySpeed = 0 ;
			this.xSpeed = 0 ;
		}
	}


	public void Update() {
		if(Level.keysPressed[3] == 1
			&& Level.playerCentered == true) {
			x -= Player.getMoveX();
		}
		if(Level.keysPressed[2] == 1
			&& Level.playerCentered == true) {
			x+=Player.getMoveX();
		}
		x += (xSpeed* frameDelta*100) - Level.xAdjust;
		y += (ySpeed* frameDelta*100) - Level.yAdjust;
		DrawQuadTex(tex,x,y,width,height);
		if(x + width < Level.xStart
				&& respawn == true){
			x = startX;
			y = startY;
		}
		if(Level.onPlatform == false){
			y += Level.gravityStrength + Level.yMove;
			}
	}

	public void drawBackgroundObject() {
		DrawQuadTex(tex,x,y,width,height);
	}

	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public static float getFrameDelta() {
		return frameDelta;
	}


	public static void setFrameDelta(float frameDelta) {
		BackgroundObjects.frameDelta = frameDelta;
	}



}
