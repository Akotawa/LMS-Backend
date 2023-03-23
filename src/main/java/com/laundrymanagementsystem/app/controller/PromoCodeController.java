package com.laundrymanagementsystem.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.PromoCodeRequestDto;
import com.laundrymanagementsystem.app.model.PromoCode;
import com.laundrymanagementsystem.app.service.IPromoCodeService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class PromoCodeController {

	@Autowired
	private IPromoCodeService promoCodeService;

	@PostMapping(value = "/promoCode/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addPromoCode(@Valid @RequestBody PromoCodeRequestDto promoCodeRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		promoCodeService.addPromoCode(promoCodeRequestDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/promoCode/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updatePromoCode(@Valid @RequestBody PromoCode promoCode) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		promoCodeService.updatePromoCode(promoCode, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/promoCode/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getPromoCodeById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		promoCodeService.getPromoCodeById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/promoCodes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllPromoCode() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		promoCodeService.getAllPromoCode(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@PostMapping(value = "/promoCode/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto applyPromoCode(@RequestParam(required = true) String promoCode) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		promoCodeService.isPromocodeValid(promoCode, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
