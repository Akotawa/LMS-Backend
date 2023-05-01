package com.laundrymanagementsystem.app.dto;

public class FeedbackRequestDto {
	private Long laundryId;
	private Long customerId;
	private Long orderId;
	private String feedback;

	public Long getLaundryId() {
		return laundryId;
	}

	public void setLaundryId(Long laundryId) {
		this.laundryId = laundryId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
