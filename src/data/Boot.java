package data;

import static org.lwjgl.opengl.GL11.*;R
import static helpers.LoadImages.*;



import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

public class Boot {
	
	public static final int WIDTH = 960, HEIGHT = 640;
	public static void main(String[] args) {
		
		System.out.println("Hello");
		BeginSession();
	 	Level level = new Level();

		//Player player2 = new Player(QuickLoad("player"),0,600,50,50);

		while (!Display.isCloseRequested()) {
			level.Update();
			//UPDATE LEVEL!!
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}


	

	

	
	public static void BeginSession() {
		Display.setTitle("Steven's Game");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace(); 
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,WIDTH,HEIGHT,0,1,-1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
		
		glShadeModel(GL_SMOOTH);        
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);                    
 
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        glClearDepth(1);  
	}







	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}
	
}
