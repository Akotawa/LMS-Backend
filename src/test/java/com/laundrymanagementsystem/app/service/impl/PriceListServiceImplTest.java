package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
import com.laundrymanagementsystem.app.dto.PriceListRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.model.PriceList;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.PriceListRepositroy;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class PriceListServiceImplTest {
	@InjectMocks
	PriceListServiceImpl priceListServiceImpl;
	@Mock
	PriceListRepositroy priceListRepositroy;
	@Mock
	LaundryRepository laundryRepository;
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
	public void addPrice() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PriceListRequestDto priceListRequestDto = new PriceListRequestDto();
		priceListRequestDto.setLaundryId(1L);
		priceListRequestDto.setPrice(1234);
		;
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		PriceList priceList = new PriceList();
		priceList.setId(1l);
		when(customMapper.PriceListRequestDtoToPriceList(priceListRequestDto)).thenReturn(priceList);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		priceListServiceImpl.addPrice(apiResponseDtoBuilder, priceListRequestDto);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("price added successfully"));

	}

	@Test
	public void getPrice() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PriceListRequestDto priceListRequestDto = new PriceListRequestDto();
		priceListRequestDto.setLaundryId(1L);
		priceListRequestDto.setPrice(1234);
		;
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		boolean test=true;
		when(priceListRepositroy.existsByLaundryId(id)).thenReturn(test);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		priceListServiceImpl.getPrice(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Success"));

	}
}
