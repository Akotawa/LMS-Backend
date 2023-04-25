package com.laundrymanagementsystem.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.ReportRequestDto;
import com.laundrymanagementsystem.app.service.IReportService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class ReportController {

	@Autowired
	private IReportService reportService;

	@PostMapping(value = "/customer/report", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addReport(@Valid @RequestBody ReportRequestDto reportRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		reportService.addReport(apiResponseDtoBuilder, reportRequestDto);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/customer/reports", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllReports() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		reportService.getAllReports(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

}
