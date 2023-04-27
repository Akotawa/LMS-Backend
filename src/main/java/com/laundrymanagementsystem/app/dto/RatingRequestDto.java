package com.laundrymanagementsystem.app.dto;

public class RatingRequestDto {
	private Long custumerId;
	private Long orderId;
	private int rating;

	public Long getCustumerId() {
		return custumerId;
	}

	public void setCustumerId(Long custumerId) {
		this.custumerId = custumerId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
