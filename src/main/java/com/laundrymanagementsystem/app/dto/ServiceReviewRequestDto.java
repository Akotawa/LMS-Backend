package com.laundrymanagementsystem.app.dto;

public class ServiceReviewRequestDto {

	private long orderId;
	private long customerId;
	private String query;
	private boolean refundRewash;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public boolean isRefundRewash() {
		return refundRewash;
	}

	public void setRefundRewash(boolean refundRewash) {
		this.refundRewash = refundRewash;
	}

}
