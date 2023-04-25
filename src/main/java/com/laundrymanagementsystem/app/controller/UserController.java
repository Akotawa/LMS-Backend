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
import com.laundrymanagementsystem.app.dto.AdminRequestDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.EmployeeRequestDto;
import com.laundrymanagementsystem.app.requestDto.CustomerRequestDto;
import com.laundrymanagementsystem.app.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping(value = "/superadmin/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addSuperAdmin(@Valid @RequestBody AdminRequestDto userRequestDto,
			final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.addSuperAdmin(userRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}

	@PutMapping(value = "/password/update/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updatePassword(@PathVariable(required = true) String password) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.updatePassword(apiResponseDtoBuilder, password);
		return apiResponseDtoBuilder.build();

	}

	@PostMapping(value = "/customer/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addUser(@Valid @RequestBody CustomerRequestDto customerRequestDto,
			final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.addCustomer(customerRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/customer/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllOrderByCustomerId(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.getAllOrder(apiResponseDtoBuilder, id);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/customer/referFriend/{id}/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addFriend(@PathVariable(required = true) long id,
			@PathVariable(required = true) String email) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.addFriend(apiResponseDtoBuilder, email, id);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/customer/forgotPassword/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto forgotPassword(@PathVariable(required = true) String email) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.forgotPassword(apiResponseDtoBuilder, email);
		return apiResponseDtoBuilder.build();
	}

	@PutMapping(value = "/customer/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateCustomer(@Valid @RequestBody CustomerRequestDto customer, @PathVariable Long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.updateCustomer(customer,id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@DeleteMapping(value = "/delete/customer/{id}")
	public ApiResponseDto deleteCustomerById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.deleteCustomerById(apiResponseDtoBuilder, id);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/employee/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto,
			final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.addEmployee(employeeRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/employee/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllEmployee() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.getAllEmployee(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/employee/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getEmployeeById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.getEmployeeById(apiResponseDtoBuilder, id);
		return apiResponseDtoBuilder.build();
	}
	@GetMapping(value = "/get/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getCustomerById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.getCustomerById(apiResponseDtoBuilder, id);
		return apiResponseDtoBuilder.build();
	}
}
