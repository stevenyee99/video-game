package data;
import static helpers.LoadImages.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;


//import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import data.Writer;
public class TextBox {
	
	private Texture tex;
	private float x,y,width,height;
	private Writer font;

	public TextBox(Texture tex,float x, float y, float width, float height) {
		this.tex = tex;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = new Writer();
		
	}
	
	public void Update() {
		DrawQuadTex(tex,x, y,width, height);
		font.updateWriting();
		//font.drawString(400, 300, "I can read and write!");
	}
}
