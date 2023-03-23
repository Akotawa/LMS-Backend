package com.laundrymanagementsystem.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.FeedbackRequestDto;

@Service
public interface IFeedbackService {

	void addFeedback(@Valid FeedbackRequestDto feedbackRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFeedbackListByLaundryId(long laundryId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFeedbackListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void sendFeedBackResponseByUserId(long userId, String response, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
