package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.RatingRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.model.Rating;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.RatingRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest {
	@InjectMocks
	RatingServiceImpl ratingServiceImpl;
	@Mock
	InventoryRepository inventoryRepository;
	@Mock
	 RatingRepository ratingRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	@Test
	public void addRating() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		RatingRequestDto ratingRequestDto = new RatingRequestDto();
		ratingRequestDto.setCustumerId(1l);
		ratingRequestDto.setOrderId(1l);
		ratingRequestDto.setRating(2);
		Rating rating = new Rating();
		rating.setId(1l);
	    when(customMapper.ratingRequestDtoToRating(ratingRequestDto)).thenReturn(rating);
		ratingServiceImpl.addRating(ratingRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.RATING_ADD_SUCCESS));

	}
	@Test
	public void getRatingListByLaundryId() {
		long laundryId=1l;
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		RatingRequestDto ratingRequestDto = new RatingRequestDto();
		ratingRequestDto.setCustumerId(1l);
		ratingRequestDto.setOrderId(1l);
		ratingRequestDto.setRating(2);
		Rating rating=new Rating();
		List<Rating> ratings =new ArrayList<>();
				when(ratingRepository.findByCustumerId(1l)).thenReturn(ratings);
		ratingServiceImpl.getRatingListByLaundryId(laundryId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}
}
