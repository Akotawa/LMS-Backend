package com.laundrymanagementsystem.app.service;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IDashboardService {

	void getAllDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

}
