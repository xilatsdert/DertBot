package dertbot;

public class DertTF {
 /**
  * @author Xilats Dert
  * @returns A transformation object to be placed in chat.
  * @params A species, a gender, and a description
  */

	private String species;
	private String gender;
	private String description;
	private String array;
	
	
	/*
	 * TODO: 
	 *       Create handling in the main class to load multi-sentence transformations.
	 *       Handling for .locks and checking for a time stamp in them. After one minute, remove the locks.
	 *		 
	 *
	 * Completed: Basic Class Setup: Created the three basic values for a tf.
	 * Create a class to handle loading in a file with a designated species and description
	 * 				
	 *
	 */
	
	
	
	//This constructor forms a TF object, a species, gender, and a description.
	public DertTF(String s, String g, String d, String m){
		
		species = s;
		gender = g;
		description = d;
		array = m;
			
	}
	
	//return the contents of the tf, for use in a description.
	
	public String getSpecies(){
		return species;
	}


	public String getGender(){
		return gender;
	}


	public String getDescription(){
		return description;
	}
	
	public String getArray(){
		return array;
	}
	
	//get the TF values, and return a DertTf object
	
	public static DertTF getTF(String line){
		//Splitter value for our CaratSV(Carat Seperated Value)
		//each time we hit the '^' sign in the given lines, it is split into certain strings.
		String splitter = "\\^";
		
		//Splitter that parses the read in line in the main class, and then chops it up.
		//The values are then assigned, and then returned.
		String[] splitted = line.split(splitter);
		
		String species = splitted[0];
	
		String gender = splitted[1];
		
		String array = splitted[2];
		
		String description = splitted[3];
		
		///Then, we return a new DertTF type, containing a species, a gender, some description, and the array it belongs to.
		//this below return code is out of order!
		return new DertTF(species, gender, description, array);
	}
	
	//Each class has a default toString() method.
	//I over-rode this one so it would print out what I wanted in a certain way.
	@Override
	public String toString() {
		return species + " of gender " + gender + " selected";
	}
	
}
