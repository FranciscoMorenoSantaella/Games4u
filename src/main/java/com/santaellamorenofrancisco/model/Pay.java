package com.santaellamorenofrancisco.model;

public class Pay {
	
	private double price;
	private String currency;
	private String method;
	private String intent;
	private String description;
	private Long user_id;
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		return "Pay [price=" + price + ", currency=" + currency + ", method=" + method + ", intent=" + intent
				+ ", description=" + description + ", user_id=" + user_id + "]";
	}
	
	
	
	
	
	
}
