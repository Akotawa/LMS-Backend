package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Report;
import com.laundrymanagementsystem.app.repository.ReportRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.dto.ReportRequestDto;
import com.laundrymanagementsystem.app.service.IReportService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service
public class ReportServiceImple implements IReportService {

	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void addReport(ApiResponseDtoBuilder apiResponseDtoBuilder, @Valid ReportRequestDto reportRequestDto) {
		if ((Utility.getSessionUser(userRepository).getRole() == 3)
				&& (Utility.getSessionUser(userRepository).getRole() != null)) {
			Report report = customMapper.reportRequestDtoToReport(reportRequestDto);
			report.setCreatedAt(new Date());
			reportRepository.save(report);
			apiResponseDtoBuilder.withMessage(Constants.REPORT_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(report);
		}
		else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getAllReports(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Report> reports = reportRepository.findAll();
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(Constants.SUCCESSFULLY).withData(reports);
	}

}
