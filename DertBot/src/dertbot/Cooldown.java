package dertbot;

import java.util.TimerTask;
public class Cooldown extends TimerTask{
	public static boolean countdown = false;
	
	//this creates a new thread to create a countdown.
	@Override
	public void run() {
		countdown = false;
		
	}

	
}
