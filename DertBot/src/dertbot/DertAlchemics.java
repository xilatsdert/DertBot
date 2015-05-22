package dertbot;
import java.util.concurrent.*;

public class DertAlchemics {
	
	public static void main(String[] args){
		
		new Thread(new DertBot("DertBotBob", "lion.anthrochat.net", "#thezoo", 6667));
		//new Thread(new DertBot("DertBotOmega", "spiral.trancefurs.net", "#TranceFurs", 6667)).start();

	}
}
