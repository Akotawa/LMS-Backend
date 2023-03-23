package com.laundrymanagementsystem.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.AdminRequestDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.EmployeeRequestDto;
import com.laundrymanagementsystem.app.dto.UserRequestDto;
import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.SuperAdmin;
import com.laundrymanagementsystem.app.model.User;

@Service
public interface IUserService {

	void addCustomer(UserRequestDto userRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	Employee findEmployeeById(Long driverId);

	User findByMobileNumber(String username);

	User findByMobileNumberOrEmail(String username, String username2);

	void save(User checkUser);

	User findById(Long id);

	void addAdmin(@Valid AdminRequestDto adminRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	void addSuperAdmin(@Valid AdminRequestDto adminRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	User getSessionUser();

	void saveEmployee(Employee customer);

	Customer findCustomerById(Long id);

	void saveCustomer(Customer customer);

	SuperAdmin findAdminByMobileNumberOrEmail(String username, String username2);

	SuperAdmin findAdminByEmail(String username);

	User findByMobileNumberOrEmailAndPassword(String username, String username2, String password);

	void addEmployee( @Valid EmployeeRequestDto employeeRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	Admin findAdminById(Long id);

	void saveAdmin(Admin admin);

	void getAllEmployee(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updatePassword(long id, ApiResponseDtoBuilder apiResponseDtoBuilder, String password);

}
