// -------------------------------------------------------------------------------
// Assignment 2
// Questions: all of part 2
// Written by: Anastasiya Bohdanova ID#40017040
// For COMP249 Section PP
// ------------------------------------------------------------------------------


/*
 * This class reads in a file of publication records sorted by publication code, has the user add
 * records, 
 * 
 * I created a file called PublicationData_Output.txt with 4 records for testing purposes.
 * Publication codes range from 101 to 104. Any publication code entered to test this program
 * must be greater than 104.
 */

import java.io.*;
import java.util.*;

public class PublicationListingProcess2 {
	
	/* NUM_PUBLICATIONS will eventually be initialized based on how many records are in the file */
	private static int NUM_PUBLICATIONS;
	/* PublicationArray size will be determined by NUM_PUBLICATIONS */
	private static Publication[] PublicationArray;
	
	private static final String FILE_NAME = "PublicationData_Output.txt";
	
	/* some variables to track iterations in the search methods */
	private static int binarySearchIterations = 0;
	private static int sequentialSearchIterations = 0;
	
	/* this will be the scanner used to read user input in console */
	private static Scanner s = new Scanner(System.in);
	
	/* still not sure what purpose this serves */
	private enum PublicationTypes {
		PUBLICATIONCODE, PUBLICATIONNAME, PUBLICATIONYEAR, PUBLICATIONAUTHORNAME, PUBLICATIONCOST, PUBLICATIONNBPAGES;
	}
	
	/* Prompts the user to enter records until they answer that they don't want to enter any more records. */
	private static void insertRowsToFile(String outfile) {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(FILE_NAME, true));
			
			/* enterAnotherRecord is set to false when the user decides to stop */
			boolean enterAnotherRecord = true;			
			while (enterAnotherRecord) {
				System.out.print("Enter publication code: ");
				long code = 0;
				
				/* the code must be an integer -- this block checks for that */
				boolean inputOk = false;
				while (!inputOk) {
					try {
						code = s.nextLong();
						inputOk = true;
					} catch (InputMismatchException e) {
						System.out.println("Please enter an integer value.");
						s.next();
					}
				}
			
				System.out.print("Enter publication name: ");
				String name = s.next();
				
				System.out.print("Enter publication year: ");
				int year = 0;
				
				/* year must be an int */
				inputOk = false;
				while (!inputOk) {
					try {
						year = s.nextInt();
						inputOk = true;
					} catch (InputMismatchException e) {
						System.out.println("Please enter an integer value.");
						s.next();
					}
				}
				
				System.out.print("Enter author name: ");
				String author = s.next();
				
				/* cost must be a number */
				System.out.print("Enter publication cost: ");
				double cost = 0.0;
				inputOk = false;
				while (!inputOk) {
					try {
						cost = s.nextDouble();
						inputOk = true;
					} catch (InputMismatchException e) {
						System.out.println("Please enter a numeric value.");
						s.next();
					}
				}
				
				/* number of pages must be an int */
				System.out.print("Enter number of pages: ");
				int pages = 0;
				inputOk = false;
				while (!inputOk) {
					try {
						pages = s.nextInt();
						inputOk = true;
					} catch (InputMismatchException e) {
						System.out.println("Please enter an integer value.");
						s.next();
					}
				}
				
				/* append the new record to the file */
				out.println(code + " " + name + " " + year + " " + author + " " + cost + " " + pages);
				
				/* ask if the user wants to enter another record & read in input */
				System.out.print("Enter another record? (y/n) ");
				String answer = s.next();
				
				/* answer must be y or n */
				while (!answer.equals("y") && !answer.equals("n")) {
					System.out.print("Invalid answer. Please type y or n: ");
					answer = s.next();
				}
				
				/* stopping condition */
				if (answer.equals("n")) {
					enterAnotherRecord = false;
				}
			}
			
			out.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	/* Takes in the name of a text file (not binary!) and prints out all the records in it. */
	private static void printFileItems(String infile) {
		try {
			/* Scanner with BufferedReader this time */
			Scanner in = new Scanner(new BufferedReader(new FileReader(infile)));
			
			/* scan lines in and print immediately */
			while (in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		}
	}
	
	/* 
	 * Standard binary search of the PublicationArray for the publication code.
	 * In the best case, the element is located in the middle of the array, which is the first element to be checked.
	 * In the worst case, it's at the very end. The worst case complexity is then O(logn) where n is the size of the array,
	 * since every iteration divides the size of the array to be searched in half. 
	 */
	private static int binaryPublicationSearch(Publication[] p, int start, int end, long code) {
		/* count is increased at the beginning of every call to binaryPublicationSearch */
		binarySearchIterations++;
		int result = 0;
		
		/* if start > end at any point, the value was not found in the array */
		if (start > end) result = -1;
		else {
			int middle = (start + end)/2;
			if (code == p[middle].getCode()) result = middle;
			/* search first half */
			else if (code < p[middle].getCode()) result = binaryPublicationSearch(p, start, middle - 1, code);
			/* search second half */
			else result = binaryPublicationSearch(p, middle + 1, end, code);
		}
		
		return result;
	}
	
	/* Standard binary search of the PublicationArray for the publication code. 
	 * Best case scenario, the element is first in the array. In the worst case, it is last. The complexity
	 * is therefore O(n), since in the worst case it will take as many iterations as there are elements in the array
	 * to find the one we're looking for.
	 */
	private static int sequentialPublicationSearch(Publication[] p, int start, int end, long code) {
		int result = -1;
		
		for (int i = 0; i < p.length; i++) {
			sequentialSearchIterations++;
			if (code == p[i].getCode()) {
				result = i;
				break;
			}
		}
		
		return result;
	}
	
	/* Counts the lines in a text file. */
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
		
		return count;
	}
	
	/* Takes in the name of a text file and reads all the records into PublicationArray. */
	private static void readInItems(String infile) {
		try {
			int index = 0;
			Scanner in = new Scanner(new FileReader(infile));
			
			/* create a new Publication object based on each line that is read in */
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
	
	/* 
	 * Takes in the name of an output file and writes all the objects in PublicationArray to it.
	 * The objects are serialized in this method, not written in text form as before. 
	 */
	private static void writeItems(String outfile) {
		try {
			/* create an object stream */
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outfile));
			
			/* write every object do binary file */
			for (int i = 0; i < NUM_PUBLICATIONS; i++) {
				out.writeObject(PublicationArray[i]);
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("=============== Assignment by Anastasiya Bohdanova ===============\n");
		 
		/* ask user to enter records */
		insertRowsToFile(FILE_NAME);
		
		/* output updated file with the records that were just entered */
		System.out.println("\n===== Updated file =====");
		printFileItems(FILE_NAME);
		
		/* count lines in file to initialize array */
		NUM_PUBLICATIONS = countLines(FILE_NAME);
		PublicationArray = new Publication[NUM_PUBLICATIONS];
		
		/* read in records & store them in PublicationArray */
		System.out.print("\nReading items into Publications array... ");
		readInItems(FILE_NAME);
		
		/* prompt the user for a code to search for */
		System.out.print("\nEnter a code to search for: ");
		long code = s.nextLong();
		
		/* perform binary search */
		System.out.println("\nRunning binary search... ");
		int index = binaryPublicationSearch(PublicationArray, 0, NUM_PUBLICATIONS, code);
		System.out.println("Code found at index " + index + " after " + binarySearchIterations + " iterations.");
		
		/* perform sequential search */
		System.out.println("\nRunning sequential search... ");
		index = sequentialPublicationSearch(PublicationArray, 0, NUM_PUBLICATIONS, code);
		System.out.println("Code found at index " + index + " after " + sequentialSearchIterations + " iterations.");
		
		/* serialize records & write to an output file */
		System.out.println("\nWriting items to Publications.dat... ");
		writeItems("Publications.dat");
		
		System.out.println("\n=============== end of program ===============\n");

	}
}