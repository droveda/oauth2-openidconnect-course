package com.mudra.fakebookapi;

/**
 * This represents a Fake Book 
 * 
 * @author viraj
 *
 */
public class FakeBook {
	
	private int id;
	private String title;
	private String author;
	private float cost;
	private int numPages;
	
	public FakeBook() {
		// Nothing here 
	}
	
	public FakeBook(int id, String title, String author, float cost, int numPages) {
		super();
		
		this.setId(id);
		this.title = title;
		this.author = author;
		this.cost = cost;
		this.numPages = numPages;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public float getCost() {
		return cost;
	}
	
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	public int getNumPages() {
		return numPages;
	}
	
	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
