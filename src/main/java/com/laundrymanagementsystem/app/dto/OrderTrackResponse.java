package com.laundrymanagementsystem.app.dto;

import com.laundrymanagementsystem.app.model.Feedback;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.Rating;

public class OrderTrackResponse {

	private Order order;
	private Feedback feedback;
	private Rating rating;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

}
