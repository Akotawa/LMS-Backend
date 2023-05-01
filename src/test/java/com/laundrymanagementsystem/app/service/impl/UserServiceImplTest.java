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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.AdminRequestDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.EmployeeRequestDto;
import com.laundrymanagementsystem.app.dto.OrderResponseDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.SuperAdmin;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.EmployeeRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.SuperAdminRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.requestDto.CustomerRequestDto;
import com.laundrymanagementsystem.app.service.IEmailService;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;
import com.laundrymanagementsystem.app.utility.Utility;

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
	@Mock
	IEmailService emailService;
	@Mock
	SuperAdminRepository superAdminRepository;
	@Mock
	IVerificationTokenService verificationTokenService;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addSuperAdmin() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		AdminRequestDto adminRequestDto = new AdminRequestDto();
		boolean s = true;
		when(userRepository.existsByEmail(adminRequestDto.getEmail())).thenReturn(s);
		userServiceImpl.addSuperAdmin(adminRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.EMAIL_ALREADY_EXISTS));

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
		boolean g = true;
		when(customerRepository.existsByEmail("test")).thenReturn(g);
		userServiceImpl.addCustomer(customerRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.EMAIL_ALREADY_EXISTS));

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
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(1);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		boolean s = true;
		when(userRepository.existsByEmail(employeeRequestDto.getEmail())).thenReturn(s);
		userServiceImpl.addEmployee(employeeRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.EMAIL_ALREADY_EXISTS));

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
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		SuperAdmin superAdmin = new SuperAdmin();
		superAdmin.setId(1l);
		Optional<SuperAdmin> g = Optional.ofNullable(superAdmin);
		when(superAdminRepository.findById(1l)).thenReturn(g);
		userServiceImpl.updatePassword(apiResponseDtoBuilder, password);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Super Admin Password update Successfully"));

	}

	@Test
	public void getAllOrder() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Order order = new Order();
		order.setId(1l);
		order.setCustomerId(1l);
		List<Order> listOfOrders = new ArrayList<>();
		listOfOrders.add(order);
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Optional<User> users = Optional.ofNullable(sessionUser);
		OrderResponseDto orderResponseDto = new OrderResponseDto();
		when(customMapper.orderToOrderResponseDto(order)).thenReturn(orderResponseDto);
		when(userRepository.findById(1l)).thenReturn(users);
		when(orderRepository.findByCustomerId(id)).thenReturn(listOfOrders);
		userServiceImpl.getAllOrder(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Success"));

	}

	@Test
	public void addFriend() {
		long id = 1l;
		String email = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Customer customer = new Customer();
		Optional<Customer> customers = Optional.ofNullable(customer);
		when(customerRepository.findById(id)).thenReturn(customers);
		userServiceImpl.addFriend(apiResponseDtoBuilder, email, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Refer your Friend Successfully"));

	}

	@Test
	public void getEmployeeById() {
		long id = 1l;
		String email = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Employee employee = new Employee();
		Optional<Employee> employees = Optional.ofNullable(employee);
		when(employeeRepository.findById(id)).thenReturn(employees);
		userServiceImpl.getEmployeeById(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}

	@Test
	public void updateCustomer() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setEmail("test");
		customerRequestDto.setFullName("test");
		customerRequestDto.setHomeAddress("test");
		customerRequestDto.setMobileNumber("123456789");
		customerRequestDto.setPassword("test");
		customerRequestDto.setProfileImage("test");
		Customer customer = new Customer();
		Optional<Customer> customers = Optional.ofNullable(customer);
		when(customerRepository.findById(id)).thenReturn(customers);
		userServiceImpl.updateCustomer(customerRequestDto, id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.CUSTOMER_UPDATE_SUCCESS));

	}

	@Test
	public void deleteCustomerById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Customer customer = new Customer();
		customer.setId(1l);
		Optional<Customer> customers = Optional.ofNullable(customer);
		when(customerRepository.findById(id)).thenReturn(customers);
		userServiceImpl.deleteCustomerById(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Customer Delete Successfully..."));

	}

	@Test
	public void forgotPassword() {
		String email = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		SuperAdmin superAdmin = new SuperAdmin();
		when(superAdminRepository.findByEmail(email)).thenReturn(superAdmin);
		// when(verificationTokenService.sendPassword("test@gmail.com", "test"));
		userServiceImpl.forgotPassword(apiResponseDtoBuilder, email);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}

	@Test
	public void sendPass() {
		String email = "test";
		String password = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userServiceImpl.sendPass(apiResponseDtoBuilder, email, password);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}

	@Test
	public void getCustomerById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		boolean b = true;
		when(customerRepository.existsById(id)).thenReturn(b);
		userServiceImpl.getCustomerById(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}
}
