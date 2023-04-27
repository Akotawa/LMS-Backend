package com.laundrymanagementsystem.app.service;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.PriceListRequestDto;

@Service
public interface IPriceListService {

	void addPrice(ApiResponseDtoBuilder apiResponseDtoBuilder, PriceListRequestDto priceListRequestDto);


	void getPrice(ApiResponseDtoBuilder apiResponseDtoBuilder, long laundryId);

}
