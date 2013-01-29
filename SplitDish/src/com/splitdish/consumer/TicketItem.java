package com.splitdish.consumer;

public class TicketItem {
	
	String title;
	String description;
	String notes;
	double price;
	
	public TicketItem(String title, String desc, String notes, double d) {
		this.title = title;
		this.description = desc;
		this.notes = notes;
		this.price = d;
	}
	
	public String toString() {
		return title;
	}
	
}
