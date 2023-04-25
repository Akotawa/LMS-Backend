package com.laundrymanagementsystem.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.ServiceRequestDto;
import com.laundrymanagementsystem.app.model.Services;

@Service
public interface IService {

	void addService(@Valid ServiceRequestDto serviceRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	void getServiceByServiceId(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllServiceByLaundryId(long laundryId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateService(@Valid Services services, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deleteServiceById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllService(ApiResponseDtoBuilder apiResponseDtoBuilder);

}
