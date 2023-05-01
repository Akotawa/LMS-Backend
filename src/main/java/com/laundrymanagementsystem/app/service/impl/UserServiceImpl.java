package com.laundrymanagementsystem.app.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.laundrymanagementsystem.app.dto.EmployeeResponseDto;
import com.laundrymanagementsystem.app.dto.OrderResponseDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.SuperAdmin;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.AdminRepository;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.EmployeeRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.SuperAdminRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.requestDto.CustomerRequestDto;
import com.laundrymanagementsystem.app.service.IEmailService;
import com.laundrymanagementsystem.app.service.IUserService;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service("userService")
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

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

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	IEmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByMobileNumberOrEmail(username, username);
		if (user == null) {
			throw new UsernameNotFoundException(Constants.INVALID_USERNAME_OR_PASSWORD);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
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
		verificationTokenService.sendVerificationToken(user, adminRequestDto.getPassword());
	}

	@Override
	public void addCustomer(CustomerRequestDto userRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		if (customerRepository.existsByMobileNumber(userRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (customerRepository.existsByEmail(userRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		Customer user = customMapper.CustomerRequestDtoToCustomer(userRequestDto);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(newPasswordEncodedString);
		user.setCreatedAt(new Date());
		user.setRole(3);
		saveCustomer(user);
		apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(user);
		verificationTokenService.sendVerificationToken(user, userRequestDto.getPassword());
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
		Employee employee = customMapper.userRequestDtoToEmployee(employeeRequestDto);
		employee.setFullName(employee.getFirstName() + " " + employee.getLastName());
		String password = Utility.generateRandomPassword(8);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(password);
		System.out.println(password);
		employee.setPassword(newPasswordEncodedString);
		employee.setCreatedAt(new Date());
		employee.setRole(2);
		saveEmployee(employee);

		EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

		employeeResponseDto.setAddress(employee.getAddress());
		employeeResponseDto.setCity(employee.getCity());
		employeeResponseDto.setDob(employee.getDob());
		employeeResponseDto.setEmail(employee.getEmail());

		employeeResponseDto.setFirstName(employee.getFirstName());
		employeeResponseDto.setLastName(employee.getLastName());
		employeeResponseDto.setMobileNumber(employee.getMobileNumber());
		employeeResponseDto.setPinCode(employee.getPinCode());
		employeeResponseDto.setCountry(employee.getCountry());

		apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK)
				.withData(employeeResponseDto);
		verificationTokenService.sendVerificationToken(employee, password);

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
		Optional<Employee> driver = employeeRepository.findById(id);
		return driver.isPresent() ? driver.get() : null;
	}

	@Override
	public void saveEmployee(Employee driver) {
		employeeRepository.save(driver);
	}

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
		List<Employee> employeeList = employeeRepository.findAll();
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(employeeList);

	}

	@Override
	public void updatePassword(ApiResponseDtoBuilder apiResponseDtoBuilder, String password) {
		if (superAdminRepository.findById(Utility.getSessionUser(userRepository).getId()).isPresent()) {
			superAdminRepository.findById(Utility.getSessionUser(userRepository).getId()).get()
					.setPassword(bCryptPasswordEncoder.encode(password));
			superAdminRepository
					.save(superAdminRepository.findById(Utility.getSessionUser(userRepository).getId()).get());
			apiResponseDtoBuilder.withMessage("Super Admin Password update Successfully").withStatus(HttpStatus.OK);
		} else if (employeeRepository.findById(Utility.getSessionUser(userRepository).getId()).isPresent()) {
			employeeRepository.findById(Utility.getSessionUser(userRepository).getId()).get()
					.setPassword(bCryptPasswordEncoder.encode(password));
			employeeRepository.save(employeeRepository.findById(Utility.getSessionUser(userRepository).getId()).get());
			apiResponseDtoBuilder.withMessage("Employee Password update Successfully").withStatus(HttpStatus.OK);
		} else if (adminRepository.findById(Utility.getSessionUser(userRepository).getId()).isPresent()) {
			adminRepository.findById(Utility.getSessionUser(userRepository).getId()).get()
					.setPassword(bCryptPasswordEncoder.encode(password));
			adminRepository.save(adminRepository.findById(Utility.getSessionUser(userRepository).getId()).get());
			apiResponseDtoBuilder.withMessage(" Admin Password update Successfully").withStatus(HttpStatus.OK);
		} else if (customerRepository.findById(Utility.getSessionUser(userRepository).getId()).isPresent()) {
			customerRepository.findById(Utility.getSessionUser(userRepository).getId()).get()
					.setPassword(bCryptPasswordEncoder.encode(password));
			customerRepository.save(customerRepository.findById(Utility.getSessionUser(userRepository).getId()).get());
			apiResponseDtoBuilder.withMessage("Customer Password update Successfully").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getAllOrder(ApiResponseDtoBuilder apiResponseDtoBuilder, long id) {
		List<Order> orderList = orderRepository.findByCustomerId(id);
		if (!orderList.isEmpty()) {
			List<OrderResponseDto> orders = orderList.stream().map(order -> {
				Optional<User> user = userRepository.findById(order.getCustomerId());
				OrderResponseDto orderResponseDto = customMapper.orderToOrderResponseDto(order);
				orderResponseDto.setFullName(user.get().getFullName());
				orderResponseDto.setId(order.getId());
				orderResponseDto.setEmail(user.get().getEmail());
				orderResponseDto.setMobileNumber(user.get().getMobileNumber());
				return orderResponseDto;
			}).collect(Collectors.toList());
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(orders);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.OK);
		}
	}

	@Override
	public void addFriend(ApiResponseDtoBuilder apiResponseDtoBuilder, String email, long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			emailService.sendEmail(email, "Refer Friend", "Url of Laundry", "welcome", null, null);
			apiResponseDtoBuilder.withMessage("Refer your Friend Successfully").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CUSTOMER_NOT_FOUND).withStatus(HttpStatus.OK);
		}
	}

	@Override
	public void getEmployeeById(ApiResponseDtoBuilder apiResponseDtoBuilder, long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
			Employee employee2 = employee.get();
			employeeResponseDto.setAddress(employee2.getAddress());
			employeeResponseDto.setCity(employee2.getCity());
			employeeResponseDto.setDob(employee2.getDob());
			employeeResponseDto.setEmail(employee2.getEmail());

			employeeResponseDto.setFirstName(employee2.getFirstName());
			employeeResponseDto.setLastName(employee2.getLastName());
			employeeResponseDto.setMobileNumber(employee2.getMobileNumber());
			employeeResponseDto.setPinCode(employee2.getPinCode());
			employeeResponseDto.setCountry(employee2.getCountry());
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK)
					.withData(employeeResponseDto);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.EMPLOYEE_NOT_FOUND).withStatus(HttpStatus.OK);
		}
	}

	@Override
	public void updateCustomer(CustomerRequestDto customerRequestDto, Long id,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Customer> customer = customerRepository.findById(id);
		Customer newCustomer = customer.get();
		if (customer.isPresent()) {
			newCustomer.setUpdatedAt(new Date());
			newCustomer.setEmail(customerRequestDto.getEmail());
//			newCustomer.setPassword(bCryptPasswordEncoder.encode(customerRequestDto.getPassword()));
			newCustomer.setFullName(customerRequestDto.getFullName());
			newCustomer.setHomeAddress(customerRequestDto.getHomeAddress());
			newCustomer.setMobileNumber(customerRequestDto.getMobileNumber());
			newCustomer.setProfileImage(customerRequestDto.getProfileImage());
			customerRepository.save(newCustomer);
			apiResponseDtoBuilder.withMessage(Constants.CUSTOMER_UPDATE_SUCCESS).withStatus(HttpStatus.OK)
					.withData(newCustomer);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CUSTOMER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteCustomerById(ApiResponseDtoBuilder apiResponseDtoBuilder, long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			customerRepository.deleteById(id);
			apiResponseDtoBuilder.withMessage("Customer Delete Successfully...").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.CUSTOMER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void forgotPassword(ApiResponseDtoBuilder apiResponseDtoBuilder, String email) {

		if (superAdminRepository.findByEmail(email) != null) {
			String password = Utility.generateRandomPassword(8);
			System.out.println(password);
			SuperAdmin sa = superAdminRepository.findByEmail(email);
			sa.setPassword(password);
			superAdminRepository.save(sa);
			sendPass(apiResponseDtoBuilder, email, password);
		} else if (adminRepository.findByEmail(email) != null) {
			String password = Utility.generateRandomPassword(8);
			System.out.println(password);
			Admin a = adminRepository.findByEmail(email);
			a.setPassword(password);
			adminRepository.save(a);
			sendPass(apiResponseDtoBuilder, email, password);
		} else if (employeeRepository.findByEmail(email) != null) {
			String password = Utility.generateRandomPassword(8);
			Employee e = employeeRepository.findByEmail(email);
			e.setPassword(password);
			employeeRepository.save(e);
			sendPass(apiResponseDtoBuilder, email, password);
		} else if (customerRepository.findByEmail(email) != null) {
			String password = Utility.generateRandomPassword(8);
			Customer c = customerRepository.findByEmail(email);
			c.setPassword(password);
			customerRepository.save(c);
			sendPass(apiResponseDtoBuilder, email, password);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}
	}

	void sendPass(ApiResponseDtoBuilder apiResponseDtoBuilder, String email, String password) {

		verificationTokenService.sendPassword(email, password);
		apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK);
	}

	@Override
	public void getCustomerById(ApiResponseDtoBuilder apiResponseDtoBuilder, long id) {

		if (customerRepository.existsById(id)) {
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK)
					.withData(customerRepository.findById(id));
		} else {
			apiResponseDtoBuilder.withMessage("customer not found").withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public boolean existsByActive(boolean b) {
		return userRepository.existsByActive(b);
	}

}