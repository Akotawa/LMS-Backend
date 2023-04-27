package com.laundrymanagementsystem.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.FeedbackRequestDto;

@Service
public interface IFeedbackService {

	void addFeedback(@Valid FeedbackRequestDto feedbackRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFeedbackListByLaundryId(long laundryId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFeedbackListByCustomerId(long customerId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void sendFeedBackResponseByFeedbackId(long feedbackId, String response, ApiResponseDtoBuilder apiResponseDtoBuilder);


}
