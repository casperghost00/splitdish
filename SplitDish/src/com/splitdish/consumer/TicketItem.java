package com.splitdish.consumer;

public class TicketItem {
	
	String title;
	String description;
	String notes;
	int price; //price in cents
	Boolean selected;
	
	public TicketItem(String title, String desc, String notes, int price) {
		this.title = title;
		this.description = desc;
		this.notes = notes;
		this.price = price;
		selected = false;
	}
	
	public String toString() {
		return title;
	}
	
}
