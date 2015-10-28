package data;

public class Triggers {
	private int xCoord,yCoord, width, height;
	private String triggerName;
	public boolean testTrigger1= false , trigger1Activated = false, testTrigger2= false, trigger2Activated = false;

	public Triggers(String name,int xCoord, int yCoord, int width, int height) {
		this.height = height;
		this.width = width;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.triggerName = name;
	}
	
	public void Update() {
		if(Player.getPlayerX() < xCoord + width
					&& Player.getPlayerX()+ Player.getWidth() > xCoord 
					&& Player.getPlayerY() + Player.getHeight() > yCoord 
					&& Player.getPlayerY() < yCoord+height 
					&& triggerName == "testTrigger1"
					&& trigger1Activated ==false
					) {
			testTrigger1 = true;
			System.out.println("Tigger Tripped");
		}	
		if(Player.getPlayerX() < xCoord + width
				&& Player.getPlayerX()+ Player.getWidth() > xCoord 
				&& Player.getPlayerY() + Player.getHeight() > yCoord 
				&& Player.getPlayerY() < yCoord+height 
				&& triggerName == "testTrigger2"
				&& trigger2Activated == false
				) {
			testTrigger2 = true;
		}	
		if(Level.keysPressed[3] == 1
				&& Level.playerCentered == true) {
			xCoord -= Player.getMoveX();
		}
		if(Level.keysPressed[2] == 1
				&& Level.playerCentered == true) {
			xCoord+=Player.getMoveX();
		}
		if(testTrigger1) {
			Writer.assignMessageQueue("The trigger worked!");
			System.out.println("Trigger 1 hit");
			trigger1Activated = true;
			testTrigger1 = false;
		}
		if(testTrigger2) {
			Writer.assignMessageQueue("The second trigger worked!");
			System.out.println("Trigger 2 hit");
			trigger2Activated = true;
			testTrigger2 = false;
		}
	}
}
