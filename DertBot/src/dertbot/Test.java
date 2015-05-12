package dertbot;

import java.io.*;



public class Test {
	
	static File tim = new File("cookie.txt");

	public static void main(String[] args)
	{
		
		testMethod();
		
	}
	
	public static void testMethod() throws IOException //the using version of java.io?
	{
		File timmy = new File("bacon.txt");
		timmy.getCanonicalFile();
	}
}
