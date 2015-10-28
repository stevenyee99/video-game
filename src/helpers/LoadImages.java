package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class LoadImages {
	
	public static void LoadImages() {
		
	}
	
	
	public static Texture LoadTexture(String path,String fileType) {
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	
	public static Texture QuickLoad(String name) {
		Texture tex = null;
		tex = LoadTexture("pictures/"+name+".png", "PNG");

		return tex;
	}
	public static Texture QuickLoadJPG(String name) {
		Texture tex = null;
		tex = LoadTexture("pictures/"+name+".jpg", "JPG");

		return tex;
	}
	
	
	public static void DrawQuadTex(Texture tex,float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(width,0);
		glTexCoord2f(1,1);
		glVertex2f(width,height);
		glTexCoord2f(0,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}

}
