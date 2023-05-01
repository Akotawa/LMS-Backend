package com.laundrymanagementsystem.app.dto;

import com.laundrymanagementsystem.app.model.Feedback;
import com.laundrymanagementsystem.app.model.Rating;

public class OrderTrackResponse {

	private OrderResponseDto orderResponseDto;
	private Feedback feedback;
	private Rating rating;

	public OrderResponseDto getOrderResponseDto() {
		return orderResponseDto;
	}

	public void setOrderResponseDto(OrderResponseDto orderResponseDto) {
		this.orderResponseDto = orderResponseDto;
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
