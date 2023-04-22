package com.laundrymanagementsystem.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class VerificationTokenController {

	@Autowired
	private IVerificationTokenService verificationTokenService;

	@GetMapping("/registrationConfirm")
	public String confirmRegistration(final Model model, @RequestParam("token") final String token) throws IOException {
		return verificationTokenService.validateToken(token);
	}

	@GetMapping(value = "resendRegistrationToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto resendRegistrationToken(final HttpServletRequest request, @RequestParam Long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		verificationTokenService.resendRegistrationToken(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
