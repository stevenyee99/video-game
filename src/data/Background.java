package data;

import org.lwjgl.input.Keyboard;

import org.newdawn.slick.opengl.Texture;
import static helpers.LoadImages.*;

public class Background {
	
	private static Texture tex;
	private static int x,y, width, height;
	
	
	public Background(Texture tex, int x, int y, int width, int height) {

		this.tex = tex;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	public void Update() {
		if(Level.onPlatform == false){
			y += Level.gravityStrength + Level.yMove;
			}
		DrawQuadTex(tex,x,y,width,height);
		
	}


	public void CenterUpdate() {
		if(Level.keysPressed[3] == 1)
				//&& Level.leftPlatform != true) {
			x -= Player.getMoveX();
		//}
		if(Level.keysPressed[2] == 1)
				//&& Level.rightPlatform != true) {
			x+=Player.getMoveX();
		//}
		if(Level.onPlatform == false){
			y += Level.gravityStrength + Level.yMove;
			}
		y -= Level.yAdjust;
		x -= Level.xAdjust;
		DrawQuadTex(tex,x,y,width,height);
	}
	public static void drawBackground() {
		DrawQuadTex(tex,x,y,width,height);
	}
	public static int getX() {
		return x;
	}
	public static void SetX(int pos) {
		x = pos;
	}
	public static void SetY(int pos) {
		y = pos;
	}

	public static int getY() {
		return y;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
}

