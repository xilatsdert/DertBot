package dertbot;
//Java Imports
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

//IRC bot imports
import org.jibble.pircbot.*;

//Each java file has a class named after it's file name, sans extension.
//That extends keyword means that Dertbot inherits from the Pircbot Library/Class
public class DertBot extends PircBot {
	
	//TODO: 
	//
	
	//static, universally accessible values for the bot.
	//this means that any tfFile value, for example, is that specific tfFile value.
	
	//TF files access and reader
	static File tfFile = new File("TFs/tf.csv");
	
	//reader of the text files
	static Scanner read;
	
	//count down timer to help prevent flooding.
	Timer cooldownTimer;
	
	//countdown control value
	static boolean controltime = false;
	
	//place holders for categorized tf's. These should be added into a master array for #randtf, and each one 
	//given their own tf commands.	
	//This is loaded into linked lists per species.

	//feline
	static List<DertTF>catList = new LinkedList<DertTF>();
	
	//canine
	static List<DertTF>dogList = new LinkedList<DertTF>();
	
	//vulpine
	static List<DertTF>foxList = new LinkedList<DertTF>();
	
	//cervine
	//static List<DertTF>deerList = new LinkedList<DertTF>();
	
	//equine
	static List<DertTF>horseList = new LinkedList<DertTF>();
	
	//draconic
	static List<DertTF>dragonList = new LinkedList<DertTF>();
	
	//racoons
	static List<DertTF>procyonList = new LinkedList<DertTF>();
	
	
	//lapine
	static List<DertTF>lapineList = new LinkedList<DertTF>();
	
	//bovine
	static List<DertTF>bovineList = new LinkedList<DertTF>();
	
	/*
	//enhancements for cyberpunk, magic punk, robotic and other forms of TF.
	
	
	//cyberpunk enhancements
	
	//magic enhancments
	
	//robotic transformation.
	
	*/
	
	//Master generic TF ArrayList
	List tfArray = new LinkedList();
	
	public DertBot(String name, String server, String room, int port) {
		
		//set the name internals
		this.setName(name);
		this.setVerbose(true);
		
		//load the tf's into the proper arrays.
		populateTFS(tfFile, catList, "feline");
		populateTFS(tfFile, dogList, "canine");
		populateTFS(tfFile, dragonList, "draconic");
		populateTFS(tfFile, foxList, "vulpine");
		populateTFS(tfFile, horseList, "equine");		
		populateTFS(tfFile, procyonList, "procyon");
		populateTFS(tfFile, lapineList, "lapine");
		populateTFS(tfFile, bovineList, "bovine");
		
		//load the tf's into the master arrays
		populateDertList(catList);
		populateDertList(dogList);
		populateDertList(horseList);
		populateDertList(foxList);
		populateDertList(procyonList);
		populateDertList(dragonList);
		populateDertList(lapineList);
		populateDertList(bovineList);
		
		//catching statements for the irc bot
		try{
		this.connect(server, port);
		this.joinChannel(room);

		}
		catch(NickAlreadyInUseException e){
			System.out.println("This user name is in use already! You FOOL!");
			e.printStackTrace();
		}
		catch(IrcException e){
			System.out.println("There was an IRC error. Please check the stack trace");
			e.printStackTrace();
		}
		
		catch(IOException e){
			System.out.println("An IO error occured. Please check stack trace");
			e.printStackTrace();
			System.exit(1);
		}
		
		//opening phrase
		this.sendAction(room, "sputters and whirrs into life, before a plume of blue smoke escapes its interior!");
		this.sendAction(room, "please type #help to get instructions in a private message!");
		
		Scanner input = new Scanner(System.in);
		String message = input.nextLine();
		
		do{
		String send = message;
		this.sendMessage(room, send);
		message = input.nextLine();
		}while(!message.equals("stop"));
	}
	
	 //various test messages to induce incremental improvements.
	//Eventually, there should be only a few methods in here that are useful.
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		
		//This loads a random TF, and replaces the word sender with the name of the user initiating the change.
		//this tf iterates via two dimensional arrays for a random tf.
		 if (message.equalsIgnoreCase("#randtf")){
	
			 randomMainTF(channel, sender);
			}
			
		//hot reloading function. This will help us add new TF's on the fly.
		 else if (message.equalsIgnoreCase("#reloadtf")){
			
			//reload TF
			reloadTF(channel, sender);
		  }
		
		 //Send to the user a listing of help files.
		else if(message.equalsIgnoreCase("#help")){
			
			helpfile(channel, sender);		
		}
		 
		//Send to the user a list of available forms.
		else if(message.equalsIgnoreCase("#list")){
			sendMessage(sender, "The current TF's lodaed are:");
			tfList(channel, sender, catList);
			tfList(channel, sender, dogList);
			tfList(channel, sender, foxList);
			tfList(channel, sender, horseList);
			tfList(channel, sender, dragonList);
			tfList(channel, sender, procyonList);
			tfList(channel, sender, lapineList);
			tfList(channel, sender, bovineList);
		}
		 
		//actual TF's based on the linked lists. 
		//if the message the bot recieves from IRC matches a certain command
		//we call the tf command, with arguments for it's species. We then send it to the channel
		//the command came from. The subject of the tf is the sender of the command.
		else if(message.equalsIgnoreCase("#kittytf")){
			
			randomSpeciesTF(catList, channel, sender);
		}
		
		else if(message.equalsIgnoreCase("#dogtf")){
			randomSpeciesTF(dogList, channel, sender);
		}
		
		else if(message.equalsIgnoreCase("#foxtf")){
			randomSpeciesTF(foxList, channel, sender);
		}
		
		else if(message.equalsIgnoreCase("#horsetf")){
			randomSpeciesTF(horseList, channel, sender);
		}
			
		else if(message.equalsIgnoreCase("#dragontf")){
			randomSpeciesTF(dragonList, channel, sender);
		}
			
		else if(message.equalsIgnoreCase("#raccoontf")){
			randomSpeciesTF(procyonList, channel, sender);
		}
			
		else if(message.equalsIgnoreCase("#bovinetf")){
			randomSpeciesTF(bovineList, channel, sender);
		}
			
		else if(message.equalsIgnoreCase("#lapinetf")){
			randomSpeciesTF(lapineList, channel, sender);
		}
	}
	
	
	//reload the tf's while the bot is in the chat room.
	//this won't load any new species into the bot.
	//just new tf's of currently coded species saved in the LinkedLists.
	private void reloadTF(String channel, String sender){
		read.close();
		tfFile = new File(" ");
		read.reset();
		tfFile = new File("TFs/tf.csv");
		
		tfArray.clear();
		catList.clear();
		dogList.clear();
		foxList.clear();
		horseList.clear();
		dragonList.clear();
		procyonList.clear();
		
		//repopulate the list.
		
		//load the tf's into the proper arrays.
		populateTFS(tfFile, catList, "feline");
		populateTFS(tfFile, dogList, "canine");
		populateTFS(tfFile, dragonList, "draconic");
		populateTFS(tfFile, foxList, "vulpine");
		populateTFS(tfFile, horseList, "equine");		
		populateTFS(tfFile, procyonList, "procyon");
		populateTFS(tfFile, dragonList, "draconic");
		populateTFS(tfFile, lapineList, "lapine");
		populateTFS(tfFile, bovineList, "bovine");
		
		//load the tf's into the master arrays
		populateDertList(catList);
		populateDertList(dogList);
		populateDertList(horseList);
		populateDertList(foxList);
		populateDertList(procyonList);
		populateDertList(dragonList);
		populateDertList(lapineList);
		populateDertList(bovineList);
			
		//notify the chat of the reload.
		sendMessage(channel, "Reloading tf file. Please try your TF command again!");
	}
	
	
	private void randomMainTF(String channel, String sender){
		if(Cooldown.countdown == false){
			setMessageDelay(1000);
			//This let's the chat know DertBot got it's instructions.
			sendAction(channel, "shakes, the screen flashing tons of colors!");
			
			//Randomly Roll a number that fits in the number range of the list, and access based by index.
			Random rand = new Random();
			int index1 = rand.nextInt(tfArray.size());
			
			//Get the description, and load the user who initiated the event's name.
			List<DertTF> picked = (List<DertTF>) (tfArray.get(index1));
			
			int index2 = rand.nextInt(picked.size());
			String description = picked.get(index2).getDescription();
			
			String user = sender;
			
			//create a holder value for the transformation description.
			String fixed;
			
			//This checks our tf description for the word Sender or sender, and replaces it.
			fixed = description.replaceAll("[Ss]ender", user);
			
			//sends out put to let the user know what we selected!
			sendMessage(channel, picked.get(index2).toString());
			
			//send out an action event to describe the TF.
			sendAction(channel, fixed);
			
			//Create, initialize, and begin cool down.
			sendAction(channel, "shakes, the screen reporting a cooldown timer of 10 seconds!");
			
			//countdown logic. That way, people don't spam the bot for TF's,
			//tripping antiflood measurements.
			//the cooldown allows us to prevent spamming.
			Cooldown.countdown = true;
			cooldownTimer = new Timer();
			cooldownTimer.schedule(new Cooldown(), 10000);
			}
		
		//If the bot is cooling down, then send a message.
		//The bot then cannot send any additional message 7.5 seconds after, or until
		//the cooldown is complete.
		else if(Cooldown.countdown == true){
			setMessageDelay(7500);
			sendAction(channel, "is still cooling down! Please wait!");
		}
	}
	
	//generic helpfile that sends a pm to a user telling them what he bot can do!.
	private void helpfile(String channel, String sender){
		sendMessage(sender, "In order to use dertBot, enter these commands in the main chatroom:");
		sendMessage(sender, "#randtf: Activate a random tf. There is a 10 second cool down after this.");
		sendMessage(sender, "#help: Obviously, you are looking at it.");
		sendMessage(sender, "#list: This will PM a list of forms loaded.");
		sendMessage(sender, "#kittytf: this selects a random cat TF.");
		sendMessage(sender, "#dogtf: this selects a random canine TF.");
		sendMessage(sender, "#horsetf: this selects a random horse TF.");
		sendMessage(sender, "#dragontf: this selects a random dragon TF.");
		sendMessage(sender, "#raccoontf: this selects a random raccoon TF.");
		sendMessage(sender, "#foxtf: this selects a random fox tf.");
		sendMessage(sender, "#lapinetf: this selects a random bunny tf.");
		sendMessage(sender, "#bovinetf: this selects a random cow tf.");
	}
	
	//Give a list of all the available tf's, per array
	private void tfList(String channel, String sender, List<DertTF> array){		
		//select an array
		for(int i = 0; i < array.size(); i++){
			sendMessage(sender, array.get(i).getSpecies() + " with a gender of " + array.get(i).getGender());		
		}
	}
	
	private void populateTFS(File file, List<DertTF> arrayToBeFilled, String search){
		
		//read in the lines from the specified files in the file array, and then fill them into the mentioned DertTF object array.
		try {
			read = new Scanner(file);
			if(read.hasNextLine() == true){
				do{
					String line = read.nextLine();
					DertTF addTF = DertTF.getTF(line);
					
					if(addTF != null){
						if(addTF.getArray().equals(search)){
							arrayToBeFilled.add(addTF);
						}
					}
				} while(read.hasNextLine() == true);
			}
		} catch(FileNotFoundException e){
			System.out.println("File not found!");
			e.printStackTrace();
		}
	}
	
	//populate the master list for random tf.
	private void populateDertList(List<DertTF> array){
		if(array.size() > 0){
			tfArray.add(array);
		}		
	}		

private void randomSpeciesTF(List<DertTF> array, String channel, String sender){
	
	if(Cooldown.countdown == false){
		
		setMessageDelay(1000);
		//This let's the chat know DertBot got it's instructions.
		sendAction(channel, "shakes, the screen flashing tons of colors!");
		
		//Randomly Roll a number that fits in the number range of the list, and access based by index.
		Random rand = new Random();
		int selection = rand.nextInt(array.size());

		String description = array.get(selection).getDescription();
		
		String user = sender;
		
		//create a holder value for the transformation description.
		String fixed;
		
		//This checks our tf description for the word Sender or sender, and replaces it.
		fixed = description.replaceAll("[Ss]ender", user);
		
		//sends out put to let the user know what we selected!
		sendMessage(channel, array.get(selection).toString());
		
		//send out an action event to describe the TF.
		sendAction(channel, fixed);
		
		//Create, initialize, and begin cool down.
		sendAction(channel, "shakes, the screen reporting a cooldown timer of 10 seconds!");
		
		//countdown logic
		Cooldown.countdown = true;
		cooldownTimer = new Timer();
		cooldownTimer.schedule(new Cooldown(), 10000);
		}
	
	else if(Cooldown.countdown == true){
		setMessageDelay(7500);
		sendAction(channel, "is still cooling down! Please wait!");
		}
	}
}