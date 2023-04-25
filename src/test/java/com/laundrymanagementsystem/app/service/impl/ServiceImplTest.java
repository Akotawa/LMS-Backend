package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import com.laundrymanagementsystem.app.dto.ServiceRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Laundry;
import com.laundrymanagementsystem.app.model.Services;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.ServiceRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceImplTest {
	@InjectMocks
	ServiceImpl serviceImpl;
	@Mock
	ServiceRepository serviceRepository;
	@Mock
	OrderRepository orderRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	LaundryRepository laundryRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addService() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
		serviceRequestDto.setLaundryId(1l);
		serviceRequestDto.setPrice(123);
		serviceRequestDto.setQuantity("test");
		serviceRequestDto.setServiceDescription("test");
		serviceRequestDto.setServiceName("test");

		Services services = new Services();
		when(customMapper.serviceRequestDtoToServices(serviceRequestDto)).thenReturn(services);
		serviceImpl.addService(serviceRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ITEM_ADD_SUCCESS));

	}

	@Test
	public void getServiceByServiceId() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
		serviceRequestDto.setLaundryId(1l);
		serviceRequestDto.setPrice(123);
		serviceRequestDto.setQuantity("test");
		serviceRequestDto.setServiceDescription("test");
		serviceRequestDto.setServiceName("test");
		Optional<Services> service = null;
		when(serviceRepository.findById(id)).thenReturn(service);
		serviceImpl.getServiceByServiceId(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ITEM_ADD_SUCCESS));

	}

	@Test
	public void getAllServiceByLaundryId() {
		long laundryId = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
		serviceRequestDto.setLaundryId(1l);
		serviceRequestDto.setPrice(123);
		serviceRequestDto.setQuantity("test");
		serviceRequestDto.setServiceDescription("test");
		serviceRequestDto.setServiceName("test");
		List<Services> listOfServices = new ArrayList<>();
		when(serviceRepository.findAllByLaundryId(laundryId)).thenReturn(listOfServices);
		serviceImpl.getServiceByServiceId(laundryId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ITEM_ADD_SUCCESS));

	}

	@Test
	public void updateService() {
		long laundryId = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
		serviceRequestDto.setLaundryId(1l);
		serviceRequestDto.setPrice(123);
		serviceRequestDto.setQuantity("test");
		serviceRequestDto.setServiceDescription("test");
		serviceRequestDto.setServiceName("test");
		List<Services> listOfServices = new ArrayList<>();
		when(serviceRepository.findAllByLaundryId(laundryId)).thenReturn(listOfServices);
		serviceImpl.getServiceByServiceId(laundryId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ITEM_ADD_SUCCESS));

	}

	@Test
	public void deleteServiceById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
		serviceRequestDto.setLaundryId(1l);
		serviceRequestDto.setPrice(123);
		serviceRequestDto.setQuantity("test");
		serviceRequestDto.setServiceDescription("test");
		serviceRequestDto.setServiceName("test");
		Services services = new Services();
		services.setId(1l);
		Optional<Services> servicesIdChaeck = Optional.ofNullable(services);
		when(serviceRepository.findById(id)).thenReturn(servicesIdChaeck);
		serviceImpl.deleteServiceById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Service Deleted Successfully"));

	}

	@Test
	public void getAllService() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Services services = new Services();
		services.setId(id);
		services.setLaundryId(1l);
		services.setPrice(123l);
		services.setServiceDescription("test");
		services.setServiceName("test");
		services.setUpdatedAt(new Date());
		services.setCreatedAt(new Date());
		List<Services> dataList = new ArrayList<Services>();
		dataList.add(services);
		when(serviceRepository.findAll()).thenReturn(dataList);
		Laundry laundry=new Laundry();
		laundry.setId(1l);
		Optional<Laundry> laundrys=Optional.ofNullable(laundry)
		when(laundryRepository.findById(services.getLaundryId());
		serviceImpl.getAllService(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ITEM_ADD_SUCCESS));

	}
}
