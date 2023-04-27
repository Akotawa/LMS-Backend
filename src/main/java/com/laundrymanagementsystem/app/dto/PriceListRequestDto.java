package com.laundrymanagementsystem.app.dto;

public class PriceListRequestDto {

	private long laundryId;
	private double price;

	public long getLaundryId() {
		return laundryId;
	}

	public void setLaundryId(long laundryId) {
		this.laundryId = laundryId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
