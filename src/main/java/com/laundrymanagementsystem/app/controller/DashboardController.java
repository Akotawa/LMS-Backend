package com.laundrymanagementsystem.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.service.IDashboardService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class DashboardController {
	
	@Autowired
	IDashboardService dashboardService;
	
	@GetMapping(value = "/get/details",produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder=new ApiResponseDtoBuilder();
		dashboardService.getAllDetails(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
