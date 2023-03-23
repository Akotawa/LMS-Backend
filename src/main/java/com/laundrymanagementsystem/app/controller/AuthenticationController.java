package com.laundrymanagementsystem.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.laundrymanagementsystem.app.config.JwtTokenUtil;
import com.laundrymanagementsystem.app.constants.AuthorizationConstants;
import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LoginUser;
import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 360000000)
@RestController
@RequestMapping(Constants.API_BASE_URL + "/auth")
public class AuthenticationController {

	private static final String TOKEN = "token";

	private static final String USER = "user";

	private static final String LOGINEDUSER = "user_details";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private IUserService userService;


	@PostMapping("/login")
	public ApiResponseDto userlogin(@RequestBody LoginUser loginUser) throws AuthenticationException {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User checkUser = userService.findByMobileNumberOrEmail(loginUser.getUsername(), loginUser.getUsername());
		if (checkUser == null) {
			apiResponseDtoBuilder.withStatus(HttpStatus.UNAUTHORIZED).withMessage(Constants.NO_Mobile_EXISTS);
			return apiResponseDtoBuilder.build();
		}
		if (checkUser.getRole() == 3) {
			Customer customer = userService.findCustomerById(checkUser.getId());
			userService.saveCustomer(customer);
		} else if (checkUser.getRole() == 2) {
			Employee employee = userService.findEmployeeById(checkUser.getId());
			userService.saveEmployee(employee);
		}else if (checkUser.getRole() == 1) {
			Admin admin = userService.findAdminById(checkUser.getId());
			userService.saveAdmin(admin);
		}
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		final UserDetails user = userDetailsService.loadUserByUsername(loginUser.getUsername());
		final String token = jwtTokenUtil.generateToken(user);

		Map<String, Object> response = setTokenDetails(user, token, checkUser);
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(AuthorizationConstants.LOGIN_SUCESSFULL)
				.withData(response);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping("/admin/login")
	public ApiResponseDto adminlogin(@RequestBody LoginUser loginUser) throws AuthenticationException {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User checkUser = userService.findByMobileNumberOrEmail(loginUser.getUsername(), loginUser.getUsername());
		if (checkUser == null) {
			apiResponseDtoBuilder.withStatus(HttpStatus.UNAUTHORIZED).withMessage(Constants.INVALID_USERNAME);
			return apiResponseDtoBuilder.build();
		}
		if (checkUser.getRole() != 0 && checkUser.getRole() != 1) {
			apiResponseDtoBuilder.withStatus(HttpStatus.UNAUTHORIZED).withMessage(Constants.UNAUTHORIZED);
			return apiResponseDtoBuilder.build();
		}
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		final UserDetails user = userDetailsService.loadUserByUsername(loginUser.getUsername());
		final String token = jwtTokenUtil.generateToken(user);
		Map<String, Object> response = setTokenDetails(user, token, checkUser);
		apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage(AuthorizationConstants.LOGIN_SUCESSFULL)
				.withData(response);
		return apiResponseDtoBuilder.build();
	}

	private Map<String, Object> setTokenDetails(final UserDetails user, final String token, final User userDetails) {
		Map<String, Object> response = new HashMap<>();
		response.put(USER, user);
		response.put(TOKEN, token);
		response.put(LOGINEDUSER, userDetails);
		return response;
	}
}
