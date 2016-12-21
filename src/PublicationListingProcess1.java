// -------------------------------------------------------------------------------
// Assignment 2
// Questions: part 1, question 3
// Written by: Anastasiya Bohdanova ID#40017040
// For COMP249 Section PP
// ------------------------------------------------------------------------------

import java.io.*;
import java.util.*;

/*
 * This class reads in publication records from a file, corrects their publication codes, and 
 * outputs the result.
 */

public class PublicationListingProcess1 {
	
	/* NUM_PUBLICATIONS will eventually be initialized based on how many records are in the file */
	private static int NUM_PUBLICATIONS;
	/* PublicationArray size will be determined by NUM_PUBLICATIONS */
	private static Publication[] PublicationArray;
	
	private static final String INPUT_FILE_NAME = "PublicationData_Input.txt";
	
	/* some variables to track iterations in the search methods */
	private static Scanner s = new Scanner(System.in);
	
	/* not sure what the purpose of this enum is supposed to be */
	private enum PublicationTypes {
		PUBLICATIONCODE, PUBLICATIONNAME, PUBLICATIONYEAR, PUBLICATIONAUTHORNAME, PUBLICATIONCOST, PUBLICATIONNBPAGES;
	}
	
	/* A method to replace all publication codes that appear more than once in the file. */
	private static void correctListOfItems(String infile, String outfile) {
		readInItems(infile);
		
		/* A nested loop to compare every publication code to every subsequent publication code. */
		for (int i = 0; i < NUM_PUBLICATIONS; i++) {
			
			/* j initialized at i+1 because only subsequent codes need to be checked */
			for (int j = i+1; j < NUM_PUBLICATIONS; j++) {
				
				/* if 2 publication codes are equal, replace the one that appears later in the file */
				if (PublicationArray[i].getCode() == PublicationArray[j].getCode()) {
					System.out.println("Item " + (j + 1) + " has the same publication code as item " + (i + 1) + ".");
					System.out.print("Please enter a new publication code for item " + (j + 1) + ": ");
					long newCode;
					
					/* codeOk is set to true if 1) the new code is an integer value and 2) it's not a copy of an existing code */
					boolean codeOk = false;
					while (!codeOk) {
						try {
							newCode = s.nextLong();
							try {
								/* checkNewCode can throw a CopyCodeException */
								checkNewCode(newCode);
								/* if no exception is thrown, codeOk is set to true */
								codeOk = true;
								PublicationArray[j].setCode(newCode);
							} catch (CopyCodeException e) {
								/* if an exception is throw, the user is prompted to replace the code */
								System.out.print(e);
								System.out.print(" Please enter a new publication code for item " + (j + 1) + ": ");
							}
						/* catches type mismatch (i.e. if user enters a non-integer value) */
						} catch (InputMismatchException e) {
							System.out.println("Please enter an integer value.");
							s.next();
						}
					}
				}
			}
		}
		
		writeItems(outfile);
	}
	
	/* Takes in a file name and reads in items from that file into PublicationArray. */ 
	private static void readInItems(String infile) {
		try {
			/* initialize PublicationArray */
			PublicationArray = new Publication[NUM_PUBLICATIONS];
			int index = 0;
			
			/* initialize Scanner */
			Scanner in = new Scanner(new FileReader(infile));
			
			/* create an object based on every line that is read in */
			while (in.hasNext()) {
				PublicationArray[index] = new Publication(in.nextLong(), in.next(),
						in.nextInt(), in.next(), in.nextDouble(), in.nextInt());
				index++;
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		}
	}
	
	/* Takes in a file name and writes items from PublicationArray to that file. */
	private static void writeItems(String outfile) {
		try {
			PrintWriter out = new PrintWriter(outfile);
			
			/* output the contents of each record to text file */
			for (int i = 0; i < NUM_PUBLICATIONS; i++) {
				out.println(PublicationArray[i].getCode() + " " + PublicationArray[i].getName() + " " + PublicationArray[i].getYear() + " " 
						+ PublicationArray[i].getAuthorName() + " " + PublicationArray[i].getCost() + " " + PublicationArray[i].getNumPages());
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		}
	}
	
	/* Takes in a file name and prints all the items in that file. */
	private static void printFileItems(String filename) {
		try {
			Scanner in = new Scanner(new FileReader(filename));
			
			while (in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		}
	}
	
	/* Takes in a file name and counts how many lines it has (for initializing PublicationArray). */
	private static int countLines(String f) {
		int count = 0;
		
		try {
			Scanner in = new Scanner(new FileReader(f));
			
			while (in.hasNextLine()) {
				in.nextLine();
				count++;
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		}
		
		return count - 1;
	}
	
	/* throws a CopyCodeException if the code entered is the same as some other publication code in the file */
	private static void checkNewCode(long code) throws CopyCodeException {
		for (int i = 0; i < NUM_PUBLICATIONS; i++) {
			if (code == PublicationArray[i].getCode()) {
				throw new CopyCodeException("Code already exists.");
			}
		}
	}

	public static void main(String[] args) {
		
		System.out.println("=============== Assignment by Anastasiya Bohdanova ===============\n");
		System.out.print("Please enter an output file name: ");
		String outputFileName = s.nextLine();
		
		/* if a file with the name the user entered already exists, prompt for a different name */
		while (new File(outputFileName).exists()) {
			System.out.println("The file " + outputFileName + " with size " + new File(outputFileName).length() +" already exists:");
			System.out.print("Please enter an output file name: ");
			outputFileName = s.nextLine();
			System.out.println("\n");
		} 
		
		/* count the lines in the file; if there are no lines or just 1 line, there is nothing to be done */
		NUM_PUBLICATIONS = countLines(INPUT_FILE_NAME);
		if (NUM_PUBLICATIONS == 0) {
			System.out.println("The input file is empty.");
		} else if (NUM_PUBLICATIONS == 1) {
			System.out.println("The input file only has one record.");
		} else {
			/* if there is more than one line, there may be corrections to be made */
			correctListOfItems(INPUT_FILE_NAME, outputFileName);
		}
		
		/* output the two files for comparison */
		System.out.println("\n===== Original file =====");
		printFileItems(INPUT_FILE_NAME);
		System.out.println("\n===== Corrected file =====");
		printFileItems(outputFileName);
		
		System.out.println("\n=============== end of program ===============\n");
	}

}