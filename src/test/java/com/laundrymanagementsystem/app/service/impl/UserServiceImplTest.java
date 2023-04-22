package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.laundrymanagementsystem.app.dto.AdminRequestDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.EmployeeRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.SuperAdmin;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.EmployeeRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.requestDto.CustomerRequestDto;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	EmployeeRepository employeeRepository;
	@Mock
	OrderRepository orderRepository;
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
	public void loadUserByUsername() {
		String username = "String";
		User user = new User();
		user.setEmail("string");
		when(userRepository.findByMobileNumberOrEmail(username, username)).thenReturn(user);
		assertTrue(userServiceImpl.loadUserByUsername(username).getUsername().equals("string"));

	}

	@Test
	public void addSuperAdmin() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		AdminRequestDto adminRequestDto = new AdminRequestDto();
		SuperAdmin superAdmin = new SuperAdmin();
		when(customMapper.adminRequestDtoToSuperAdmin(adminRequestDto)).thenReturn(superAdmin);
		userServiceImpl.addSuperAdmin(adminRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_ADD_SUCCESS));

	}

	@Test
	public void addCustomer() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setEmail("test");
		customerRequestDto.setFullName("test");
		customerRequestDto.setHomeAddress("test");
		customerRequestDto.setMobileNumber("12345678");
		customerRequestDto.setPassword("test");
		customerRequestDto.setProfileImage("test");
		Customer customer = new Customer();
		when(customMapper.CustomerRequestDtoToCustomer(customerRequestDto)).thenReturn(customer);
		userServiceImpl.addCustomer(customerRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_ADD_SUCCESS));

	}

	@Test
	public void addEmployee() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
		employeeRequestDto.setAddress("test");
		employeeRequestDto.setEmail("test");
		employeeRequestDto.setMobileNumber("12345678");
		employeeRequestDto.setUserType("test");
		Employee customer = new Employee();
		when(customMapper.userRequestDtoToEmployee(employeeRequestDto)).thenReturn(customer);
		userServiceImpl.addEmployee(employeeRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_ADD_SUCCESS));

	}

	@Test
	public void getAllEmployee() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Employee> employeeList = new ArrayList<>();
		when(employeeRepository.findAll()).thenReturn(employeeList);
		userServiceImpl.getAllEmployee(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void updatePassword() {
		String password = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userServiceImpl.updatePassword(apiResponseDtoBuilder, password);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void getAllOrder() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Order> listOfOrders = new ArrayList<>();
		when(orderRepository.findByCustomerId(id)).thenReturn(listOfOrders);
		userServiceImpl.getAllOrder(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void addFriend() {
		long id = 1l;
		String email = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Optional<Customer> customer = null;
		when(customerRepository.findById(id)).thenReturn(customer);
		userServiceImpl.addFriend(apiResponseDtoBuilder, email, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void getEmployeeById() {
		long id = 1l;
		String email = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Optional<Employee> employee = null;
		when(employeeRepository.findById(id)).thenReturn(employee);
		userServiceImpl.getEmployeeById(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void updateCustomer() {
		long id = 1l;
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setEmail("test");
		customerRequestDto.setFullName("test");
		customerRequestDto.setHomeAddress("test");
		customerRequestDto.setMobileNumber("123456789");
		customerRequestDto.setPassword("test");
		customerRequestDto.setProfileImage("test");

		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Optional<Customer> customer = null;
		when(customerRepository.findById(id)).thenReturn(customer);
		userServiceImpl.updateCustomer(customerRequestDto, id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void deleteCustomerById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Optional<Customer> customer = null;
		when(customerRepository.findById(id)).thenReturn(customer);
		userServiceImpl.deleteCustomerById(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void forgotPassword() {
		String email = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userServiceImpl.forgotPassword(apiResponseDtoBuilder, email);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}
	@Test
	public void sendPass() {
		String email = "test";
		String password="test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userServiceImpl.sendPass(apiResponseDtoBuilder, email,password);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}
	@Test
	public void getCustomerById() {
		long id=1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		when(customerRepository.existsById(id));
		userServiceImpl.getCustomerById(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}
}
