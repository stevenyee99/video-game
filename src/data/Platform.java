package data;

import static helpers.LoadImages.DrawQuadTex;


import org.newdawn.slick.opengl.Texture;

public class Platform {
	
	private Texture texture;
	private int x,y, height, width;
	private int[] coordinates = {x,x+width,y,y+height};
	
	public Platform(String type,int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = helpers.LoadImages.QuickLoad(type);
		
	}
	public int[] getCoordinates() {
		return coordinates;
	}


	public void Update() {
		if(Level.onPlatform == false){
			y += Level.gravityStrength + Level.yMove;
			}
		DrawQuadTex(texture,x,y,width,height);
	}
	public void CenterUpdate() {
		if(Level.keysPressed[3] == 1) {
			x -= Player.getMoveX();
		}
		if(Level.keysPressed[2] == 1) {
			x+=Player.getMoveX();
		}
		if(Level.onPlatform == false){
			y += Level.gravityStrength + Level.yMove;
			
			}
		y -= Level.yAdjust;
		x -= Level.xAdjust;
		DrawQuadTex(texture,x,y,width,height);
	}
	public void drawPlatform() {
		DrawQuadTex(texture,x,y,width,height);
	}
	
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public int getHeight() {
		return height;
	}


	public int getWidth() {
		return width;
	}




	
	
}
