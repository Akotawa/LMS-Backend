package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LaundryRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Laundry;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.PromoCodeRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class LaundryServiceImplTest {
	@InjectMocks
	LaundryServiceImpl laundryServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	LaundryRepository laundryRepository;
	@Mock
	PromoCodeRepository promoCodeRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addLaundry() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		LaundryRequestDto laundryRequestDto = new LaundryRequestDto();
		laundryRequestDto.setCity("test");
		laundryRequestDto.setCompanyName("test");
		laundryRequestDto.setMobileNumber("8741880744");
		laundryRequestDto.setCountry("test");
		laundryRequestDto.setEmail("test");
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(0);
		Laundry laundry = new Laundry();
		boolean s = true;
		when(userRepository.existsByEmail(laundryRequestDto.getEmail())).thenReturn(s);
		when(customMapper.laundryRequestDtoTolandry(laundryRequestDto)).thenReturn(laundry);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		laundryServiceImpl.addLaundry(laundryRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Email Already Exists"));

	}

	@Test
	public void updateLaundry() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Laundry laundry = new Laundry();
		laundry.setBusinessType("test");
		laundry.setCity("test");
		laundry.setCompanyName("test");
		laundry.setCountry("test");
		laundry.setCreatedAt(new Date());
		laundry.setEmail("test@gmail.com");
		laundry.setFirstName("test");
		laundry.setId(1l);
		laundry.setLastName("test");
		laundry.setLaundryStatus(true);
		laundry.setMobileNumber("8741814874");
		laundry.setPassword("test");
		laundry.setUpdatedAt(new Date());
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(0);
		Laundry laundrytest = new Laundry();
		Optional<Laundry> user = Optional.ofNullable(laundrytest);
		when(laundryRepository.findById(laundry.getId())).thenReturn(user);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		laundryServiceImpl.updateLaundry(laundry, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Laundry  Update Successfully."));

	}

	@Test
	public void getAllLaundryDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		laundryServiceImpl.getAllLaundryDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}

	@Test
	public void getLaundryDetailsByUserId() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(0);
		Laundry laundry = new Laundry();
		laundry.setId(1l);
		Optional<Laundry> user = Optional.ofNullable(laundry);
		when(laundryRepository.findById(id)).thenReturn(user);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		laundryServiceImpl.getLaundryDetailsByUserId(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}

	@Test
	public void deleteLaundryById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		laundryServiceImpl.getLaundryDetailsByUserId(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("You are not authorized"));

	}

	@Test
	public void isActiveLaundry() {
		long id = 1l;
		boolean active = true;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(0);
		Laundry laundry = new Laundry();
		laundry.setId(1l);
		Optional<Laundry> user = Optional.ofNullable(laundry);
		when(laundryRepository.findById(id)).thenReturn(user);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		laundryServiceImpl.isActiveLaundry(id, active, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Laundry Status Change successfully.."));

	}

	@Test
	public void ChangeOOStatus() {
		long id = 1l;
		int status = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Laundry laundry = new Laundry();
		laundry.setId(1l);
		Optional<Laundry> user = Optional.ofNullable(laundry);
		when(laundryRepository.findById(id)).thenReturn(user);
		laundryServiceImpl.ChangeOOStatus(id, status, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Laundry approved"));

	}

	@Test
	public void orderConfirmation() {
		long id = 1l;
		int status = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Laundry laundry = new Laundry();
		laundry.setId(1l);
		Optional<Laundry> user = Optional.ofNullable(laundry);
		when(laundryRepository.findById(id)).thenReturn(user);
		laundryServiceImpl.orderConfirmation(id, status, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Laundry approved"));

	}
}
