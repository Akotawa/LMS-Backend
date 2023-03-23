package com.laundrymanagementsystem.app.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.AdminRequestDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.EmployeeRequestDto;
import com.laundrymanagementsystem.app.dto.UserRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.SuperAdmin;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.AdminRepository;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.EmployeeRepository;
import com.laundrymanagementsystem.app.repository.SuperAdminRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.service.IUserService;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service("userService")
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmployeeRepository driverRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private SuperAdminRepository superAdminRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private IVerificationTokenService verificationTokenService;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByMobileNumberOrEmail(username, username);
		if (user == null) {
			throw new UsernameNotFoundException(Constants.INVALID_USERNAME_OR_PASSWORD);
		}
		return new org.springframework.security.core.userdetails.User(user.getMobileNumber(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public void addAdmin(AdminRequestDto adminRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		if (userRepository.existsByMobileNumber(adminRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(adminRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		Admin user = customMapper.adminRequestDtoToAdmin(adminRequestDto);
		// String password ="123456";// Utility.generateRandomPassword(8);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(adminRequestDto.getPassword());
		user.setPassword(newPasswordEncodedString);
		user.setCreatedAt(new Date());
		user.setRole(1);
		saveAdmin(user);
		apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(user);
		verificationTokenService.sendVerificationToken(user);
	}

	@Override
	public void addSuperAdmin(AdminRequestDto adminRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		if (userRepository.existsByMobileNumber(adminRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(adminRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		SuperAdmin user = customMapper.adminRequestDtoToSuperAdmin(adminRequestDto);
		// String password ="123456";// Utility.generateRandomPassword(8);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(adminRequestDto.getPassword());
		user.setPassword(newPasswordEncodedString);
		user.setCreatedAt(new Date());
		user.setRole(0);
		superAdminRepository.save(user);
		apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(user);
		verificationTokenService.sendVerificationToken(user);
	}

	

	@Override
	public void addCustomer(UserRequestDto userRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		if (userRepository.existsByMobileNumber(userRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(userRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		Customer user = customMapper.userRequestDtoToCustomer(userRequestDto);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(newPasswordEncodedString);
		user.setCreatedAt(new Date());
		user.setRole(3);
		saveCustomer(user);
		apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(user);
		verificationTokenService.sendVerificationToken(user);
	}

	@Override
	public void addEmployee(@Valid EmployeeRequestDto employeeRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		User currentUser = getSessionUser();
		if (currentUser == null || currentUser.getRole() != 1) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			return;
		}
		if (userRepository.existsByMobileNumber(employeeRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(employeeRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		Employee user = customMapper.userRequestDtoToEmployee(employeeRequestDto);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(newPasswordEncodedString);
		user.setCreatedAt(new Date());
		user.setRole(2);
		saveEmployee(user);

		apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(user);
		verificationTokenService.sendVerificationToken(user);

	}

	@Override
	public User findByMobileNumber(String username) {
		return userRepository.findByMobileNumber(username);
	}

	@Override
	public User findByMobileNumberOrEmail(String mobileNumber, String email) {
		return userRepository.findByMobileNumberOrEmail(mobileNumber, email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	public void saveAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	public User getSessionUser() {
		return Utility.getSessionUser(userRepository);
	}

	@Override
	public Customer findCustomerById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.isPresent() ? customer.get() : null;
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public Employee findEmployeeById(Long id) {
		Optional<Employee> driver = driverRepository.findById(id);
		return driver.isPresent() ? driver.get() : null;
	}

	@Override
	public void saveEmployee(Employee driver) {
		driverRepository.save(driver);
	}

//	@Override
//	public Admin findAdminByMobileNumberOrEmail(String username, String username2) {
//		return adminRepository.findByMobileNumberOrEmail(username, username2);
//	}
//
//	@Override
//	public Admin findAdminByEmail(String username) {
//		return adminRepository.findByEmail(username);
//	}

	@Override
	public User findByMobileNumberOrEmailAndPassword(String username, String username2, String password) {
		return userRepository.findByMobileNumberOrEmailAndPassword(username, username2, password);
	}

	@Override
	public Admin findAdminById(Long id) {
		Optional<Admin> admin = adminRepository.findById(id);
		return admin.isPresent() ? admin.get() : null;
	}

	@Override
	public SuperAdmin findAdminByMobileNumberOrEmail(String username, String username2) {
		// TODO Auto-generated method stub
		return superAdminRepository.findByMobileNumberOrEmail(username, username2);
	}

	@Override
	public SuperAdmin findAdminByEmail(String username) {
		return superAdminRepository.findByEmail(username);
	}

	@Override
	public void getAllEmployee(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		 List<Employee> employeeList = driverRepository.findAll();
		 apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(employeeList);
		
		
	}

	@Override
	public void updatePassword(long id, ApiResponseDtoBuilder apiResponseDtoBuilder,String password) {
		Optional<SuperAdmin> superAdmin = superAdminRepository.findById(id);
		if (superAdmin.isPresent()) {
			superAdmin.get().setPassword(password);
			superAdminRepository.save(superAdmin.get());
			apiResponseDtoBuilder.withMessage("Super Admin Password update Successfully").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage("Super Admin not fond.").withStatus(HttpStatus.OK);
		}
	}

}