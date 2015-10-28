package data;

import static helpers.LoadImages.*;
import static data.Boot.*;
import static helpers.Clock.*;

import helpers.Clock;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Keyboard;



public class Level {
	
	public static int platformsYCoord, platformsXCoord, platformsRightXCoord, xAdjust, yAdjust;
	public static int spaceDiff, levelEnd, xStart = 0,yMove,moveY, gravityStrength= -15;
	public static int[] keysPressed = {0,0,0,0};
	public static int timeDelta;
	private ArrayList<Platform> platforms;
	private ArrayList<BackgroundObjects> objects;
	private ArrayList<Triggers> triggers;
	//private Platform platform, base;  Not used
	//private Ground ground;  Not used
	private Background background;
	private Player player;
	private TextBox textbox;
	private static String direction;
	public static boolean playerCentered, onPlatform, leftPlatform, rightPlatform;

	
	public Level () {
		this.platforms = new ArrayList<Platform>();
		this.triggers = new ArrayList<Triggers>();
		this.objects = new ArrayList<BackgroundObjects>(); 
		this.background = new Background(QuickLoadJPG("background"),0,0,2048,1024);
		this.player = new Player(QuickLoad("mario_sprites"),550,200,10,20);
		this.textbox = new TextBox(QuickLoadJPG("text_box"),0,HEIGHT - 100,WIDTH,100);
		
		
		objects.add(new BackgroundObjects(QuickLoad("cloud"),400,100,50,50,true,"left"));
		objects.add(new BackgroundObjects(QuickLoad("cloud"),800,150,50,50,true,"left"));
		objects.add(new BackgroundObjects(QuickLoad("cloud"),1600,200,50,50,true,"left"));
		objects.add(new BackgroundObjects(QuickLoad("cloud"),680,120,50,50,true,"left"));
		objects.add(new BackgroundObjects(QuickLoad("cloud"),2000,183,50,50,true,"left"));
		objects.add(new BackgroundObjects(QuickLoad("cloud"),1220,223,50,50,true,"left"));
		
		
		objects.add(new BackgroundObjects(QuickLoad("reddot"),4100,400,400,100,false,"none"));
		objects.add(new BackgroundObjects(QuickLoad("reddot"),2100,400,400,100,false,"none"));
		
		triggers.add(new Triggers("testTrigger1",4100,400,400,100));
		triggers.add(new Triggers("testTrigger2",2100,400,400,100));
		
		platforms.add(new Platform("ground",330,275,100,10)); //First roof
		platforms.add(new Platform("ground",685,330,100,10)); // 2nd roof
		platforms.add(new Platform("ground",770,260,10,100)); //Flagpole
		platforms.add(new Platform("ground",480,390,200,10)); //Clothesline
		platforms.add(new Platform("ground",830,360,370,10)); //Crane
		platforms.add(new Platform("ground",1275,415,145,10)); //Roof blip thingy
		platforms.add(new Platform("ground",1100,445,450,10));  //Posters roof
		platforms.add(new Platform("ground",1555,300,10,160)); //Smokestack wall
		platforms.add(new Platform("ground",1100,445,10,130));  //poster building left wall
		platforms.add(new Platform("ground",955,475,45,5)); //Crane's platform lowered
		platforms.add(new Platform("ground",770,330,10,200));  //2nd building's right wall
		platforms.add(new Platform("ground",780,530,70,10)); //2nd buildings little side roof
		platforms.add(new Platform("ground",840,530,10,45)); //2nd buildings little side wall
		platforms.add(new Platform("ground",280,575,775,10)); //Left side ground
		
		
		platforms.add(new Platform("ground",1030,570,10,65)); //Sewer wall left
		platforms.add(new Platform("ground",1040,655,125,10));  //Sewer floor 1
		platforms.add(new Platform("ground",1260,655,110,10));  //Sewer floor 2
		platforms.add(new Platform("ground",1455,650,160,10));  //Sewer floor 3
		platforms.add(new Platform("ground",1610,615,80,10)); //Sewer floor 4
		platforms.add(new Platform("ground",1690,575,10,40));  //Sewer wall right
		platforms.add(new Platform("ground",1620,300,10,275));  //Poster building right
		platforms.add(new Platform("ground",0,640,200,10));

		platforms.add(new Platform("ground",400,700,200,10));
		platforms.add(new Platform("ground",400,690,200,10));
		platforms.add(new Platform("ground",400,680,200,10));
		platforms.add(new Platform("ground",400,660,200,10));
		
	}
	
	public void Update() {
		System.out.println(xAdjust);
		helpers.Clock.update();
		SetAdjust();
		IsPlayerCentered();
		HitPlatform();
		KeyPressed();
		//NotJumping();
		Player.MoveSpeed();
		BackgroundObjects.setFrameDelta(helpers.Clock.d);
		if (playerCentered == true) {
			xStartUpdate();
			background.CenterUpdate();	
			for(BackgroundObjects o : objects){
				o.Update();
				}
			
			for(Platform p : platforms) {
					p.CenterUpdate();			
				}
			Player.CenterUpdate();
		} else {
			background.Update();
			for(BackgroundObjects o : objects){
				o.Update();
			}
			for(Platform p : platforms){
				p.Update();
			}
			Player.Update();
		}
		textbox.Update();
		for(Triggers t : triggers) {
			t.Update();
		}
	}
	
	
	public void HitPlatform() {
		onPlatform = false;
		leftPlatform = false;
		rightPlatform = false;
		for (Platform p : platforms) {
			//On Top
			if(Player.getPlayerX() + Player.getWidth() > p.getX() 
					&& Player.getPlayerX() < p.getX() + p.getWidth() 
					&& Player.getPlayerY() + Player.getHeight() < p.getY() + 5 
					&& Player.getPlayerY() + Player.getHeight() > p.getY() - 20
					) {
				yAdjust = p.getY() - Player.getPlayerY() - Player.getHeight();
				onPlatform = true;
			}
			//Left side of platform
			if(Player.getPlayerX() + Player.getWidth() > p.getX()-4
					&& Player.getPlayerX() + Player.getWidth()  < p.getX() + 10
					&& Player.getPlayerY() + Player.getHeight() > p.getY()
					&& Player.getPlayerY() < p.getY() + p.getHeight() + 5
					) {
				if(p.getHeight() < Player.getHeight()/3
						&& Player.jumping) {
					xAdjust = p.getX() - Player.getPlayerX() - Player.getWidth() ;
					leftPlatform = true;
				}
				if(p.getHeight() > Player.getHeight()/3) {
					xAdjust = p.getX() - Player.getPlayerX() - Player.getWidth() ;
					leftPlatform = true;
				}
						
				//this.platformsXCoord =  p.getX();
			}
			//Right side of platform
			if(Player.getPlayerX() > p.getX() + p.getWidth()-7
					&& Player.getPlayerX() < p.getX()+ p.getWidth() + 8
					&& Player.getPlayerY() + Player.getHeight() > p.getY()
					&& Player.getPlayerY() < p.getY() + p.getHeight() + 5
					) {
				if(p.getHeight() < Player.getHeight()/3
						&& Player.jumping) {
					xAdjust = Player.getPlayerX() - p.getX() - p.getWidth();
					rightPlatform = true;
				}
				if(p.getHeight() > Player.getHeight()/3) {
					xAdjust = Player.getPlayerX() - p.getX() - p.getWidth();
					rightPlatform = true;
				}
				//this.platformsRightXCoord =  p.getX() + p.getWidth();
				//playerCentered = false;
			}
		}
	}
	
	
	public static void setOnPlatform(boolean onPlatform) {
		Level.onPlatform = onPlatform;
	}

	public static void KeyPressed() {
		keysPressed[0] = 0;
		keysPressed[1] = 0;
		keysPressed[2] = 0;
		keysPressed[3] = 0;
			//Order is U, D, L, R
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			{
				keysPressed[3] = 1;
				Player.setDirection("r");
				}	
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				keysPressed[2] =1;
				Player.setDirection("l");
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP) && onPlatform == true) {
				keysPressed[0] = 1;
				Player.jumping = true;
				Player.Jump();
				}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) 
				keysPressed[1] = 1;
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) 
				Player.setPlayerY(0);
	}
	
	public void NotJumping() {
		if(Player.jumping != true) {
			Player.changeY = 10;
			yMove = 0;
		}
	}
	

	public void IsPlayerCentered() {
		playerCentered = false;
		if((Player.getPlayerX() + (Player.getWidth()/2)) > ((WIDTH/2)-25) &&
				(Player.getPlayerX() + (Player.getWidth()/2)) < ((WIDTH/2)+25)) {
			if (background.getX() + background.getWidth() < WIDTH +8
					&&background.getX() -  + background.getWidth() > WIDTH - 7  
					&& keysPressed[3] == 1) {
				playerCentered = false;
				}
			else if (background.getX() > -10
					&& background.getX() < 10
					&& keysPressed[2] == 1) {
				background.SetX(0);
				playerCentered = false;
				}
			else {
				playerCentered = true;
			}
		}
	}
	public static void xStartUpdate() {
		if(playerCentered == true
				&& keysPressed[2] == 1) {
			xStart += Player.getMoveX();
		}
		if(playerCentered == true
				&& keysPressed[3] == 1) {
			xStart -= Player.getMoveX();
		}
	}
	public void CenterUpdate() {
		xStartUpdate();
		background.CenterUpdate();	
		//BackgroundObjects.setFrameDelta(Clock.getDelta());
		/*
		for(BackgroundObjects o : objects){
			o.Update();
		}
		
		for(Platform p : platforms) {
				p.CenterUpdate();			
		}
		Player.Update();
		*/
	}

	private void SetAdjust() {
		xAdjust = 0;
		yAdjust = 0;
	}
	
	public void moveUp() {
		int move = 0;
		if(onPlatform == true
				&& Player.getPlayerY() > 400
				&& keysPressed[1] == 1) {
			move = -5;
		}
		if(move != 0){
		//if(onPlatform == true
		//		&& Player.getPlayerY() < 100) {
		//	move = 5;
		//}
			System.out.println("Moving");
			while (Player.getPlayerY() >200)
			{
				Player.setPlayerY(Player.getPlayerY() +move);
				Player.DrawCharacter();
				Background.SetY(Background.getY() + move);
				Background.drawBackground();
				for(Platform p : platforms){
					p.setY(p.getY()+move);
					p.drawPlatform();
				}
				for(BackgroundObjects o : objects){
					o.setY(o.getY()+move);
					o.drawBackgroundObject();
				}

			}
					
		}
	}

	public void OldUpdate() {
		if(Player.yMove !=0){
			System.out.println(spaceDiff);
		}
		helpers.Clock.currentTime = helpers.Clock.getTime();
		KeyPressed();
		IsPlayerCentered();
		HitPlatform();
			if (playerCentered == true) {
				CenterUpdate();
			}else {
				background.Update();
				BackgroundObjects.setFrameDelta(Clock.getDelta());
				for(BackgroundObjects o : objects){
					o.Update();
				}
				for(Platform p : platforms){
					p.Update();
				Player.Update();
				
			}
			}
		Player.PlatformCheck();
		textbox.Update();
		for(Triggers t : triggers) {
			t.Update();
		
		}
		//moveUp();
		
		
	}
}

