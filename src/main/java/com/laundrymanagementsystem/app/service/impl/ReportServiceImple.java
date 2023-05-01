package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Report;
import com.laundrymanagementsystem.app.repository.ReportRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.dto.ReportRequestDto;
import com.laundrymanagementsystem.app.service.IEmailService;
import com.laundrymanagementsystem.app.service.IReportService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service
public class ReportServiceImple implements IReportService {

	@Autowired
	private Environment environment;

	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	IEmailService emailService;

	@Override
	public void addReport(ApiResponseDtoBuilder apiResponseDtoBuilder, @Valid ReportRequestDto reportRequestDto) {
		if ((Utility.getSessionUser(userRepository).getRole() == 3)
				&& (Utility.getSessionUser(userRepository).getRole() != null)) {
			Report report = customMapper.reportRequestDtoToReport(reportRequestDto);
			report.setCreatedAt(new Date());
			reportRepository.save(report);
			String body = "<html><body><h3>Hello Admin</h3>"
					+ "<br>Someone contacted or reported a problem, please check details here - <br>email : "
					+ report.getEmail() + "<br>subject : " + report.getSubject() + "<br>message : "
					+ report.getMessage()
					+ "<br><br>Kind Regards,<br>Team Laundry Management System<br>Thank You</body></html>";
			emailService.sendEmail(environment.getProperty(Constants.SUPPORT_EMAIL),
					"Important! Customer Reported a problem or contacted", body, "Report", null, null);
			apiResponseDtoBuilder.withMessage(Constants.REPORT_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(report);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getAllReports(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Report> reports = reportRepository.findAll();
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(Constants.SUCCESSFULLY).withData(reports);
	}

}
