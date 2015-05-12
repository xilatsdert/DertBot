package dertbot;

import java.util.LinkedList;
import java.util.List;


/**
 * @author XilatsDert
 * This is the main class to load the bots we need
 */
//IRC bot imports
import org.jibble.pircbot.*;

public class DertBotMain extends PircBot {
	
	
	static List<DertBot>dertBotList = new LinkedList<DertBot>();

	//main execution method
	
	public static void main(String[] args){
		
		new DertBot("DertBotAlpha", "lion.anthrochat.net", "#DertAlchemics", 6667);
		new DertBot("DertBotBeta", "spiral.trancefurs.net", "DertRandD", 6667);
		
		
		for(int i = 0; i <= dertBotList.size() - 1; i++){
			dertBotList.get(i);
		}
	}
}