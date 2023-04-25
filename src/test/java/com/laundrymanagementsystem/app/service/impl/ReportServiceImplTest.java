package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.ReportRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Report;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.ReportRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
	@InjectMocks
	ReportServiceImple reportServiceImple;
	@Mock
	ReportRepository reportRepository;
	@Mock
	OrderRepository orderRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addReport() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ReportRequestDto reportRequestDto = new ReportRequestDto();
		reportRequestDto.setEmail("test");
		reportRequestDto.setMessage("test");
		reportRequestDto.setSubject("test");
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(3);
		Report report = new Report();
		report.setId(1l);
		when(customMapper.reportRequestDtoToReport(reportRequestDto)).thenReturn(report);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		reportServiceImple.addReport(apiResponseDtoBuilder, reportRequestDto);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.REPORT_ADD_SUCCESS));

	}

	@Test
	public void getAllReports() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Report> reports = new ArrayList<>();
		when(reportRepository.findAll()).thenReturn(reports);
		reportServiceImple.getAllReports(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}
}
