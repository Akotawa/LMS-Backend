package com.laundrymanagementsystem.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.PromoCodeRequestDto;
import com.laundrymanagementsystem.app.model.PromoCode;

@Service
public interface IPromoCodeService {

	void addPromoCode(@Valid PromoCodeRequestDto promoCodeRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updatePromoCode(@Valid PromoCode promoCode, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getPromoCodeById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllPromoCode(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isPromocodeValid(String promoCode, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deletePromoCodeById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
