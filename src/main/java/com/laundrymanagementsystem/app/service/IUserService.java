package com.laundrymanagementsystem.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.AdminRequestDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.EmployeeRequestDto;
import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.SuperAdmin;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.requestDto.CustomerRequestDto;

@Service
public interface IUserService {

	void addCustomer(@Valid CustomerRequestDto customerRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	Employee findEmployeeById(Long driverId);

	User findByMobileNumber(String username);

	User findByMobileNumberOrEmail(String username, String username2);

	void save(User checkUser);

	User findById(Long id);

	void addSuperAdmin(@Valid AdminRequestDto adminRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	User getSessionUser();

	void saveEmployee(Employee customer);

	Customer findCustomerById(Long id);

	void saveCustomer(Customer customer);

	SuperAdmin findAdminByMobileNumberOrEmail(String username, String username2);

	SuperAdmin findAdminByEmail(String username);

	User findByMobileNumberOrEmailAndPassword(String username, String username2, String password);

	void addEmployee(@Valid EmployeeRequestDto employeeRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	Admin findAdminById(Long id);

	void saveAdmin(Admin admin);

	void getAllEmployee(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updatePassword(ApiResponseDtoBuilder apiResponseDtoBuilder, String password);

	void getAllOrder(ApiResponseDtoBuilder apiResponseDtoBuilder, long id);

	void addFriend(ApiResponseDtoBuilder apiResponseDtoBuilder, String email, long id);

	void getEmployeeById(ApiResponseDtoBuilder apiResponseDtoBuilder, long id);

	void updateCustomer(@Valid CustomerRequestDto customer, Long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deleteCustomerById(ApiResponseDtoBuilder apiResponseDtoBuilder, long id);

	void forgotPassword(ApiResponseDtoBuilder apiResponseDtoBuilder, String email);

	void getCustomerById(ApiResponseDtoBuilder apiResponseDtoBuilder, long id);

}
