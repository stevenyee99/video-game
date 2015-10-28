package helpers;

import org.lwjgl.Sys;

public class Clock {

	public static boolean paused= false;
	public static long lastFrame, totalTime, currentTime;
	public static float d = 0, multiplier = 1;
	
	
	public static long getTime() {
		return Sys.getTime() * 1000/Sys.getTimerResolution();
	}
	
	public static float getDelta() {
		currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		if (delta *0.001f > .05f) {
			return 0.03f;
		}
		return delta *0.001f;
	}
	public static float Delta() {
		if(paused)
			return 0;
		else 
			return d * multiplier;	
	}
	public static void Pause() {
		if (paused) {
			paused = false;
		} else {
			paused = true;
		}
		
	}
	
	
	public static float TotalTime() {
		return totalTime;
	}
	public static float Multipier() {
		return multiplier;
	}
	public static void update() {
		d = getDelta();
		totalTime += d;
	}
	public static void ChangeMultiplier(int change) {
		if (multiplier + change < -1 && multiplier + change > 7) {
			
		} else {
		multiplier += change;
		}
	}

}
