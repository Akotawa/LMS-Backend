package com.laundrymanagementsystem.app.service;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.User;

@Service
public interface IVerificationTokenService {

	String validateToken(String token);

	void resendRegistrationToken(Long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void sendVerificationToken(User user, String password);

	void sendBookingToken(User sessionUser);

	void sendWelcomeToken(String password, String email);

	void sendPassword(String email, String password);

	void sendBill(User user, Order order);


}
