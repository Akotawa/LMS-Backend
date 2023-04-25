package com.laundrymanagementsystem.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.ServiceRequestDto;
import com.laundrymanagementsystem.app.dto.ServiceResponseDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Services;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.PriceListRepositroy;
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
	@Autowired
	PriceListRepositroy priceListRepositroy;

	@Override
	public void addService(@Valid ServiceRequestDto serviceRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		Services serviceRequestDtoToServices = customMapper.serviceRequestDtoToServices(serviceRequestDto);
		serviceRequestDtoToServices.setCreatedAt(new Date());
		serviceRepository.save(serviceRequestDtoToServices);
		apiResponseDtoBuilder.withMessage("Service Add Sucessfully").withStatus(HttpStatus.OK)
				.withData(serviceRequestDtoToServices);
	}

	@Override
	public void getServiceByServiceId(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Services> service = serviceRepository.findById(id);
		if (service.isPresent()) {

			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(service);

		} else {
			apiResponseDtoBuilder.withMessage("data not fond.").withStatus(HttpStatus.OK);

		}
	}

	@Override
	public void getAllServiceByLaundryId(long laundryId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Services> listOfServices = serviceRepository.findAllByLaundryId(laundryId);

		if (!listOfServices.isEmpty()) {

			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(listOfServices);

		} else {
			apiResponseDtoBuilder.withMessage("data not fond.").withStatus(HttpStatus.OK);
		}
	}

	@Override
	public void updateService(@Valid Services services, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Services> servicesChaeck = serviceRepository.findById(services.getId());
		if (servicesChaeck.isPresent()) {
			services.setUpdatedAt(new Date());
			serviceRepository.save(services);
			apiResponseDtoBuilder.withMessage("Service Update Successfully.").withStatus(HttpStatus.OK)
					.withData(services);
		} else {
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
			apiResponseDtoBuilder.withMessage(Constants.NO_SERVICE_EXISTS).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void getAllService(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<ServiceResponseDto> serviceList = new ArrayList<>();
		List<Services> dataList = serviceRepository.findAll();
		for (Services services : dataList) {
			ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
			serviceResponseDto.setServiceId(services.getId());
			serviceResponseDto.setServiceName(services.getServiceName());
			serviceResponseDto.setLaundryId(services.getLaundryId());
			if (laundryRepository.findById(services.getLaundryId()).isPresent()) {
				serviceResponseDto
						.setLaundryName(laundryRepository.findById(services.getLaundryId()).get().getCompanyName());
			}
			serviceResponseDto.setPriceId(services.getPrice());
			if(priceListRepositroy.findById(services.getPrice()).isPresent()) {
				serviceResponseDto.setPrice(priceListRepositroy.findById(services.getPrice()).get().getPrice());
			}
			serviceList.add(serviceResponseDto);
		}
		apiResponseDtoBuilder.withMessage("Service List").withStatus(HttpStatus.OK).withData(serviceList);
	}
}
