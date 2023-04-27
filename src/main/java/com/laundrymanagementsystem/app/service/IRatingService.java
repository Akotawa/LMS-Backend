package com.laundrymanagementsystem.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.RatingRequestDto;

@Service
public interface IRatingService {

	void addRating(@Valid RatingRequestDto ratingRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);


	void getRatingListByLaundryId(long laundryId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	
}
