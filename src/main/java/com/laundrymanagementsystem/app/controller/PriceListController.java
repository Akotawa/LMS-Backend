package com.laundrymanagementsystem.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.PriceListRequestDto;
import com.laundrymanagementsystem.app.service.IPriceListService;

@RestController
@RequestMapping(Constants.API_BASE_URL)
@CrossOrigin(origins = "*", maxAge = 36000000)
public class PriceListController {

	@Autowired
	IPriceListService priceListService;

	@PostMapping(value = "/add/Price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addPrice(@RequestBody PriceListRequestDto priceListRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		priceListService.addPrice(apiResponseDtoBuilder, priceListRequestDto);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/get/Price/{laundryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getPricebyLaundryId(@PathVariable(required = true) long laundryId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		priceListService.getPrice(apiResponseDtoBuilder, laundryId);
		return apiResponseDtoBuilder.build();
	}
}
