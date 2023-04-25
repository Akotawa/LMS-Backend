package com.laundrymanagementsystem.app.dto;

public class InventoryRequestDto {

	private String userName;
	private String email;
	private Long laundryId;
	private String itemName;
	private long quantity;
	private String payment;
	private String itemDescription;

	public Long getLaundryId() {
		return laundryId;
	}

	public void setLaundryId(Long laundryId) {
		this.laundryId = laundryId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

}
