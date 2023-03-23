package com.laundrymanagementsystem.app.service;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.User;

@Service
public interface IVerificationTokenService {

	String validateToken(String token);

	void resendRegistrationToken(Long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void sendVerificationToken(User user);

	void sendVerificationToken(Order order);

	void sendBookingToken(Order order);

}
