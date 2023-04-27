package com.laundrymanagementsystem.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LaundryMachineAssignmentDto;
import com.laundrymanagementsystem.app.service.ILaundryMachineAssignment;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class LaundryMachineAssignmentController {
	@Autowired
	ILaundryMachineAssignment laundryMachineAssignment;

	@PostMapping(value = "/add/LaundryMachineAssing", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addLaundryMachineAssign(
			@RequestBody LaundryMachineAssignmentDto LaundryMachineAssignmentDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		laundryMachineAssignment.addLaundryMachineAssign(apiResponseDtoBuilder, LaundryMachineAssignmentDto);
		return apiResponseDtoBuilder.build();
	}
}
