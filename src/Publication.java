// -------------------------------------------------------------------------------
// Assignment 2
// Questions: part 1, question 1
// Written by: Anastasiya Bohdanova ID#40017040
// For COMP249 Section PP
// ------------------------------------------------------------------------------

import java.io.Serializable;

/* 
 * The publication class contains attributes representing the attributes of real life publications.
 * 
 * Publication must implement the Serializable interface since we're going to be writing 
 * it to a binary file in PublicationListingProcess2. 
*/

public class Publication implements Serializable {
	private long publication_code;
	private String publication_name;
	private int publication_year;
	private String publication_authorname;
	private double publication_cost;
	private int publication_nbpages;
	
	public Publication() {
		publication_code = 0;
		publication_name = "";
		publication_year = 0;
		publication_authorname = "";
		publication_cost = 0.0;
		publication_nbpages = 0;
	}
	
	public Publication(long code, String name, int year, String authorname, double cost, int nbpages) {
		publication_code = code;
		publication_name = name;
		publication_year = year;
		publication_authorname = authorname;
		publication_cost = cost;
		publication_nbpages = nbpages;
	}
	
	public long getCode() {
		return publication_code;
	}
	
	public String getName() {
		return publication_name;
	}
	
	public int getYear() {
		return publication_year;
	}
	
	public String getAuthorName() {
		return publication_authorname;
	}
	
	public double getCost() {
		return publication_cost;
	}
	
	public int getNumPages() {
		return publication_nbpages;
	}
	
	public void setCode(long code) {
		publication_code = code;
	}
	
	public void setName(String name) {
		publication_name = name;
	}
	
	public void setYear(int year) {
		publication_year = year;
	}
	
	public void setAuthorName(String authorname) {
		publication_authorname = authorname;
	}
	
	public void setCost(double cost) {
		publication_cost = cost;
	}
	
	public void setNumPages(int nbpages) {
		publication_nbpages = nbpages;
	}
	
}
