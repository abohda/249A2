// -------------------------------------------------------------------------------
// Assignment 2
// Questions: part 1, question 2
// Written by: Anastasiya Bohdanova ID#40017040
// For COMP249 Section PP
// ------------------------------------------------------------------------------


/*
 * A custom exception class. Thrown when a publication code entered by the user is a duplicate
 * of another publication code already in the file.
 */

public class CopyCodeException extends Exception {
	public CopyCodeException(String s) {
		super(s);
	}
}
