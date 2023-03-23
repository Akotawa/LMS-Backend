package com.laundrymanagementsystem.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LaundryRequestDto;
import com.laundrymanagementsystem.app.model.Laundry;

@Service
public interface lLaundryService {

	void addLaundry(@Valid LaundryRequestDto laundryRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	void updateLaundry(@Valid Laundry laundry, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllLaundryDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getLaundryDetailsByUserId(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deleteLaundryById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isActiveLaundry(long id, boolean active, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void ChangeOOStatus(long id, int status, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void orderConfirmation(long id, int status, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
