package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ServiceReviewRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.ServiceReview;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.ServiceReviewRepository;
import com.laundrymanagementsystem.app.service.IServiceReview;

@Service
public class ServiceReviewImpl implements IServiceReview {

	@Autowired
	ServiceReviewRepository serviceReviewRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomMapper customMapper;

	@Override
	public void addServiceReview(ApiResponseDtoBuilder apiResponseDtoBuilder,
			ServiceReviewRequestDto serviceReviewRequestDto) {
		if ((orderRepository.findById(serviceReviewRequestDto.getOrderId()).isPresent())
				&& (customerRepository.findById(serviceReviewRequestDto.getCustomerId()).isPresent())) {
			ServiceReview serviceReview = customMapper.serviceReviewRequestDtoToServiceReview(serviceReviewRequestDto);
			serviceReview.setCreatedAt(new Date());
			serviceReviewRepository.save(serviceReview);
			apiResponseDtoBuilder.withMessage("Service Review ..").withStatus(HttpStatus.OK).withData(serviceReview);
		}
		else {
			apiResponseDtoBuilder.withMessage("Data Not Found ...").withStatus(HttpStatus.NOT_FOUND);
		}
	}

}
