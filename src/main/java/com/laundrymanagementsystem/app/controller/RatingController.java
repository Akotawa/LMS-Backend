package com.laundrymanagementsystem.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.RatingRequestDto;
import com.laundrymanagementsystem.app.service.IRatingService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class RatingController {

	@Autowired 
	private IRatingService ratingService;

	@PostMapping(value = "rating/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addRating(@Valid @RequestBody RatingRequestDto ratingRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ratingService.addRating(ratingRequestDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "ratings/get/{laundryid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getRatingListByLaundryid(@PathVariable(required = true) long laundryid) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ratingService.getRatingListByLaundryId(laundryid, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
