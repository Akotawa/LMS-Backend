package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.model.VerificationToken;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.VerificationTokenRepository;
import com.laundrymanagementsystem.app.service.IUserService;

@ExtendWith(MockitoExtension.class)
public class VerificationTokenServiceImplTest {
	@InjectMocks
	VerificationTokenServiceImpl verificationTokenServiceImpl;
	@Mock
	VerificationTokenRepository verificationTokenRepository;
	@Mock
	OrderRepository orderRepository;
	@Mock
	IUserService userService;
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
	public void validateToken() {
		String token = "test";
		VerificationToken verificationToken = new VerificationToken();
		when(verificationTokenRepository.findByToken(token)).thenReturn(verificationToken);
		assertTrue(verificationTokenServiceImpl.validateToken(token).equals("Thank you for verify your email!!"));

	}

	@Test
	public void resendRegistrationToken() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		long id = 1l;
		User user = new User();
		when(userService.findById(id)).thenReturn(user);
		verificationTokenServiceImpl.resendRegistrationToken(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Confirmation email has been sent"));

	}

	@Test
	public void sendVerificationToken() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		long id = 1l;
		String password = "test";
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		verificationTokenServiceImpl.sendVerificationToken(sessionUser, password);

	}

	@Test
	public void sendBookingToken() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		long id = 1l;
		String password = "test";
		Order order = new Order();
		order.setCustomerName("test");
		order.setEmail("test@gmail.com");
		order.setId(1l);
		verificationTokenServiceImpl.sendBookingToken(order);

	}
}
