package com.laundrymanagementsystem.app.service;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LaundryMachineAssignmentDto;

@Service
public interface ILaundryMachineAssignment {

	void addLaundryMachineAssign(ApiResponseDtoBuilder apiResponseDtoBuilder,
			LaundryMachineAssignmentDto laundryMachineAssignmentDto);

}
