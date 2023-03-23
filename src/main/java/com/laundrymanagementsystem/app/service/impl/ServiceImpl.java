package com.laundrymanagementsystem.app.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.ServiceRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Services;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.ServiceRepository;
import com.laundrymanagementsystem.app.service.IService;

@Service
public class ServiceImpl implements IService {

	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	CustomMapper customMapper;
	@Autowired
	LaundryRepository laundryRepository;

	@Override
	public void addService(@Valid ServiceRequestDto serviceRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		Services serviceRequestDtoToServices = customMapper.serviceRequestDtoToServices(serviceRequestDto);
		serviceRepository.save(serviceRequestDtoToServices);
		apiResponseDtoBuilder.withMessage("Service Add Sucessfully").withStatus(HttpStatus.OK)
				.withData(serviceRequestDtoToServices);
	}

	@Override
	public void getServiceByServiceId(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Services> service = serviceRepository.findById(id);
		if (service.isPresent()) {

			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(service);

		} else {
			apiResponseDtoBuilder.withMessage("data not fond.").withStatus(HttpStatus.OK);

		}
	}

	@Override
	public void getServiceByLaundryId(long laundryId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Services> laundryIdCheck = serviceRepository.findByLaundryId(laundryId);
		
		if (laundryIdCheck.isPresent()) {

			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(laundryIdCheck);

		} else {
			apiResponseDtoBuilder.withMessage("data not fond.").withStatus(HttpStatus.OK);

		}
	}

	@Override
	public void updateService(@Valid Services services, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Services> servicesChaeck = serviceRepository.findById(services.getId());
		if(servicesChaeck.isPresent()) {
			serviceRepository.save(services);
			apiResponseDtoBuilder.withMessage("Service Update Successfully.").withStatus(HttpStatus.OK)
					.withData(services);
		}else {
			apiResponseDtoBuilder.withMessage(Constants.NO_SERVICE_EXISTS).withStatus(HttpStatus.OK);
			return;
		}
		
	}

	@Override
	public void deleteServiceById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Services> servicesIdChaeck = serviceRepository.findById(id);
		if (servicesIdChaeck.isPresent()) {
			serviceRepository.deleteById(id);

			apiResponseDtoBuilder.withMessage("Service Deleted Successfully").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}
		
	}
}
