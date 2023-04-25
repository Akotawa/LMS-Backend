package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.AdminRepository;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.EmployeeRepository;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.ServiceRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceImplTest {
	@InjectMocks
	DashboardServiceImpl dashboardServiceImpl;
	@Mock
	InventoryRepository inventoryRepository;
	@Mock
	OrderRepository orderRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private LaundryRepository laundryRepository;

	@Mock
	private AdminRepository adminRepository;

	@Mock
	private ServiceRepository serviceRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void getAllDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(0);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		dashboardServiceImpl.getAllDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}
}
