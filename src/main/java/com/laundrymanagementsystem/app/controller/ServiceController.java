package com.laundrymanagementsystem.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.ServiceRequestDto;
import com.laundrymanagementsystem.app.model.Services;
import com.laundrymanagementsystem.app.service.IService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class ServiceController {

	// addService

	@Autowired
	private IService iService;

	@PostMapping(value = "/Service/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addService(@Valid @RequestBody ServiceRequestDto serviceRequestDto,
			final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iService.addService(serviceRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}

	// getServiceByServiceId
	@GetMapping(value = "/service/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getServiceByServiceId(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iService.getServiceByServiceId(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	// getServicesByLaundryId
	@GetMapping(value = "/service/getByLaundryId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getServiceByLaundryId(@RequestParam(required = true) long laundryId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iService.getAllServiceByLaundryId(laundryId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	// updateService
	@PutMapping(value = "/service/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateService(@Valid @RequestBody Services services) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iService.updateService(services, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	// deleteServiceByServiceId
	@DeleteMapping(value = "/service/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto deleteServiceById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iService.deleteServiceById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/service/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllService() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iService.getAllService(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

}
