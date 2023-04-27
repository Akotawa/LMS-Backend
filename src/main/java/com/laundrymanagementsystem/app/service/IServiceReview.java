package com.laundrymanagementsystem.app.service;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.ServiceReviewRequestDto;

@Service
public interface IServiceReview  {

	void addServiceReview(ApiResponseDtoBuilder apiResponseDtoBuilder, ServiceReviewRequestDto serviceReviewRequestDto);

}
