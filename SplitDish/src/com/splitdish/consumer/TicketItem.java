package com.splitdish.consumer;

public class TicketItem {
	
	String title;
	String description;
	String notes;
	float price;
	
	public TicketItem(String title, String desc, String notes, float price) {
		this.title = title;
		this.description = desc;
		this.notes = notes;
		this.price = price;
	}
	
}
