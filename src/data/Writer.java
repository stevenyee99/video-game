package data;

import java.awt.Font;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
 
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
 
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;




public class Writer {
	private static TrueTypeFont font2;
	public static String message, line1 ="",line2="", moreStory;
	public static List<String> story = new ArrayList<String>(); List<String> arrayLine1 = new LinkedList<String>(); List<String> arrayLine2 = new LinkedList<String>();
	//private static ArrayList<String> story = new ArrayList<String>();ArrayList<String> arrayLine1 = new ArrayList<String>() ; ArrayList<String> arrayLine2 = new ArrayList<String>() ;
	public int messageLen;
	private static int lastSpacePosition;
	private static long time=helpers.Clock.getTime(),time2;
	public Writer() {
		init();
	}
		
	public void init() {
		//story.add("There once was a man from Nantucket");
		//story.add("And the man cooked an ostrich egg that tasted like gouda cheese");
		moreStory = "I am currently reading a book called 'The Life of Pi'.  It is a very interesting novel about religion, self, and spending a lot of time in a boat with a tiger called Richard parker."; 
		assignMessageQueue(moreStory);
		message = story.get(0);
		story.remove(0);
		//time = helpers.Clock.getTime();
		getMessageLines();
 
		// load font from file
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("pictures/jane_font.ttf");
 
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(Font.BOLD,36f);
			font2 = new TrueTypeFont(awtFont2, true);

 
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Font awtFont = new Font("Times New Roman", Font.BOLD, 24); //name, style (PLAIN, BOLD, or ITALIC), size
	}

	public static void assignMessageQueue(String message) {
		if(message.length()>60) {
			lastSpacePosition = 60;
			lastSpacePosition = getLastSpace(message.substring(0, 60));
			story.add(message.substring(0,lastSpacePosition-1));	
			assignMessageQueue(message.substring(lastSpacePosition, message.length()));	
			
		} else {
			
			story.add(message);
		}
	}
	
	private void getMessageLines() {
		if(message.length() < 30) {
			arrayLine1 = new LinkedList<String>(Arrays.asList((message.substring(0, message.length()).split(" "))));
			 line2 = "";
		} else if(message.length() < 60) {
			lastSpacePosition = 30;
			lastSpacePosition = getLastSpace(message.substring(0, 30));
			arrayLine1 = new LinkedList<String>(Arrays.asList((message.substring(0, lastSpacePosition).split(" "))));
			arrayLine2 = new LinkedList<String>(Arrays.asList((message.substring(lastSpacePosition, message.length()).split(" "))));
		}
		//List<String> list = new LinkedList (Arrays.asList(split));
		
	}
	private static int getLastSpace(String message) {
		if(lastSpacePosition == 1) {
			return 31;
		}
		if((message.substring(message.length()-1,message.length()).contains(" "))==true) {
			return lastSpacePosition;
		} 
		lastSpacePosition -= 1;
		return getLastSpace(message.substring(0, message.length() -1));
	}
	
	private void getNextMessage() {
		if(helpers.Clock.currentTime-time > 3000) {
			if(story.isEmpty() == false) {
				line1 = "";
				line2 = "";
				message = story.get(0);
				story.remove(0);
				getMessageLines();
				time = helpers.Clock.getTime();
				
			}
		}
		if(helpers.Clock.currentTime-time2 > 250) {
			if(arrayLine1.isEmpty()==false) {
				line1 = line1 + " " + arrayLine1.get(0);
				arrayLine1.remove(0);
			}
			else if(arrayLine1.isEmpty() && arrayLine2.isEmpty()==false) {
				line2 = line2 + " " + arrayLine2.get(0);
				arrayLine2.remove(0);
			}
			//if(arrayLine1.isEmpty() && arrayLine2.isEmpty()) {	
			//}
			time2 = helpers.Clock.getTime();
		}
		
	}
	public void updateWriting() {
		getNextMessage();
		Color.white.bind();
		//font.drawString(100, 50, "THE LIGHTWEIGHT JAVA GAMES LIBRARY", org.newdawn.slick.Color.yellow);
		font2.drawString(Boot.WIDTH/2 -350, 560, line1, Color.black);
		font2.drawString(Boot.WIDTH/2- 350, 590, line2, Color.black);
		font2.drawString(100, 100, "", Color.white);
	}
	
}


