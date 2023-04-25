package com.laundrymanagementsystem.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LaundryRequestDto;
import com.laundrymanagementsystem.app.model.Laundry;
import com.laundrymanagementsystem.app.service.lLaundryService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class LaundryController {

	@Autowired
	private lLaundryService laundryService;

	@PostMapping(value = "/laundry/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addLaundry(@Valid @RequestBody LaundryRequestDto laundryRequestDto,
			final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		laundryService.addLaundry(laundryRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}

	@PutMapping(value = "/laundry/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateLaundry(@Valid @RequestBody Laundry laundry) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		laundryService.updateLaundry(laundry, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/laundry", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllLaundryDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		laundryService.getAllLaundryDetails(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/laundry/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getLaundryDetailsByUserId(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		laundryService.getLaundryDetailsByUserId(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@DeleteMapping(value = "/laundry/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto deleteLaundryById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		laundryService.deleteLaundryById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PutMapping(value = "/laundry/{id}/{active}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto isActiveLaundry(@PathVariable(required = true) long id,
			@PathVariable(required = true) boolean active) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		laundryService.isActiveLaundry(id, active, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

//	@PostMapping(value = "/laundry/ChangeOOStatus/{id}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ApiResponseDto ChangeOnlineOfflineStatus(@PathVariable(required = true) long id,
//			@PathVariable(required = true) int status) {
//		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
//		laundryService.ChangeOOStatus(id, status, apiResponseDtoBuilder);
//		return apiResponseDtoBuilder.build();
//	}
	@PostMapping(value = "/laundry/order/confirmation/{id}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto orderConfirmation(@PathVariable(required = true) long id,
			@PathVariable(required = true) int status) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		laundryService.orderConfirmation(id, status, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

}
