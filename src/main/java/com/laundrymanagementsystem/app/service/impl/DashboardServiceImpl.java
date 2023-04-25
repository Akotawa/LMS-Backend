package com.laundrymanagementsystem.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.DashboardRequestDto;
import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.AdminRepository;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.EmployeeRepository;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.ServiceRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.service.IDashboardService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service
public class DashboardServiceImpl implements IDashboardService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private LaundryRepository laundryRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Override
	public void getAllDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() == 0) {
			DashboardRequestDto dashboardRequestDto = new DashboardRequestDto();
			dashboardRequestDto.setTotalOrder(orderRepository.count());
			dashboardRequestDto.setTotalEmployee(employeeRepository.count());
			dashboardRequestDto.setTotalLaundry(laundryRepository.count());
			dashboardRequestDto.setTotalCustomer(customerRepository.count());
			dashboardRequestDto.setTotalAdmin(adminRepository.count());
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardRequestDto);
		} else if (sessionUser.getRole() == 1) {
			DashboardRequestDto dashboardRequestDto = new DashboardRequestDto();
			Optional<Admin> admin = adminRepository.findById(sessionUser.getId());
			dashboardRequestDto.setTotalOrder(orderRepository.countByLaundryId(admin.get().getLaundryid()));
			dashboardRequestDto.setTotalEmployee(employeeRepository.countByLaundryid(admin.get().getLaundryid()));
			dashboardRequestDto.setTotalService(serviceRepository.countByLaundryId(admin.get().getLaundryid()));
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardRequestDto);
		} else if (sessionUser.getRole() == 2) {
			Optional<Employee> employee = employeeRepository.findById(sessionUser.getId());
			DashboardRequestDto dashboardRequestDto = new DashboardRequestDto();
			dashboardRequestDto.setTotalPendingOrder(
					orderRepository.countByOrderStatusAndLaundryId(0, employee.get().getLaundryid()));
			dashboardRequestDto.setTotalReceivedOrder(
					orderRepository.countByOrderStatusAndLaundryId(1, employee.get().getLaundryid()));
			dashboardRequestDto.setTotalCompletedOrder(
					orderRepository.countByOrderStatusAndLaundryId(2, employee.get().getLaundryid()));
			dashboardRequestDto.setTotalCancelOrder(
					orderRepository.countByOrderStatusAndLaundryId(3, employee.get().getLaundryid()));
			dashboardRequestDto.setTotalDeliveredOrder(
					orderRepository.countByOrderStatusAndLaundryId(4, employee.get().getLaundryid()));
			dashboardRequestDto.setTotalOrder(orderRepository.countByLaundryId(employee.get().getLaundryid()));
			dashboardRequestDto.setTotalService(serviceRepository.countByLaundryId(employee.get().getLaundryid()));
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardRequestDto);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}
}
