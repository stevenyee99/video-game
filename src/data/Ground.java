package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.LoadImages.*;

public class Ground {
	
	private static Texture tex;
	private static int x,y, width, height;
	
	
	public Ground(Texture tex, int x, int y, int width, int height) {

		this.tex = tex;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	public void Update() {
		DrawQuadTex(tex,x,y,width,height);
		
	}
}
