package com.laundrymanagementsystem.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.ReportRequestDto;

@Service
public interface IReportService {

	void addReport(ApiResponseDtoBuilder apiResponseDtoBuilder, @Valid ReportRequestDto reportRequestDto);

	void getAllReports(ApiResponseDtoBuilder apiResponseDtoBuilder);

}
