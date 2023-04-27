package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
import com.laundrymanagementsystem.app.dto.ServiceReviewRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.ServiceReview;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.ServiceReviewRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceReviewImplTest {
	@InjectMocks
	ServiceReviewImpl serviceReviewImpl;
	@Mock
	ServiceReviewRepository serviceReviewRepository;
	@Mock
	OrderRepository orderRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	CustomerRepository customerRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addServiceReview() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		ServiceReviewRequestDto serviceReviewRequestDto = new ServiceReviewRequestDto();
		serviceReviewRequestDto.setCustomerId(1l);
		serviceReviewRequestDto.setOrderId(1l);
		serviceReviewRequestDto.setQuery("test");
		serviceReviewRequestDto.setRefundRewash(true);

		Order order = new Order();
		order.setId(1l);
		Optional<Order> s = Optional.ofNullable(order);
		when(orderRepository.findById(1l)).thenReturn(s);

		Customer customer = new Customer();
		Optional<Customer> customerList = Optional.ofNullable(customer);
		when(customerRepository.findById(1l)).thenReturn(customerList);
		ServiceReview serviceReview =new ServiceReview();
		when(customMapper.serviceReviewRequestDtoToServiceReview(serviceReviewRequestDto)).thenReturn( serviceReview);
		serviceReviewImpl.addServiceReview(apiResponseDtoBuilder, serviceReviewRequestDto);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Service Review .."));

	}

}
