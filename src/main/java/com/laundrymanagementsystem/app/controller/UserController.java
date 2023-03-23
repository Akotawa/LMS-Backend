package com.laundrymanagementsystem.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.AdminRequestDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.EmployeeRequestDto;
import com.laundrymanagementsystem.app.dto.UserRequestDto;
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
	@PostMapping(value = "/password/update{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updatePassword(@PathVariable(required = true) long id,String password) {
		ApiResponseDtoBuilder apiResponseDtoBuilder=new ApiResponseDtoBuilder();
		userService.updatePassword(id,apiResponseDtoBuilder,password);
		return apiResponseDtoBuilder.build();
		
	}
	@PostMapping(value = "/admin/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addAdmin(@Valid @RequestBody AdminRequestDto userRequestDto,
			final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.addAdmin(userRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}
	@PostMapping(value = "/customer/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addUser(@Valid @RequestBody UserRequestDto userRequestDto, final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.addCustomer(userRequestDto, apiResponseDtoBuilder, request);
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
		ApiResponseDtoBuilder apiResponseDtoBuilder =new ApiResponseDtoBuilder();
		userService.getAllEmployee(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
		
	}
}
