package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.RatingRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Rating;
import com.laundrymanagementsystem.app.repository.RatingRepository;
import com.laundrymanagementsystem.app.service.IRatingService;

@Service
public class RatingServiceImpl implements IRatingService {
	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private CustomMapper customMapper;

	@Override
	public void addRating(@Valid RatingRequestDto ratingRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		if (!ratingRepository.existsByCustumerId(ratingRequestDto.getCustumerId())) {
			Rating rating = customMapper.ratingRequestDtoToRating(ratingRequestDto);
			rating.setCreatedAt(new Date());
			ratingRepository.save(rating);
			apiResponseDtoBuilder.withMessage(Constants.RATING_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(rating);
		} else {
			apiResponseDtoBuilder.withMessage("Rating already exist").withStatus(HttpStatus.ALREADY_REPORTED);
		}
	}

	@Override
	public void getRatingListByLaundryId(long laundryId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Rating> ratings = ratingRepository.findByCustumerId(laundryId);
		apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(ratings);

	}

}
