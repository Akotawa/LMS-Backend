package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LaundryRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.Laundry;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.AdminRepository;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.PriceListRepositroy;
import com.laundrymanagementsystem.app.repository.ServiceRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;
import com.laundrymanagementsystem.app.service.lLaundryService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service
public class LaundryServiceImpl implements lLaundryService {
	@Autowired
	LaundryRepository laundryRepository;
	@Autowired
	CustomMapper customMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private IVerificationTokenService verificationTokenService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private PriceListRepositroy priceListRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void addLaundry(@Valid LaundryRequestDto laundryRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser == null || currentUser.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			return;
		}
		if (laundryRepository.existsByMobileNumber(laundryRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByMobileNumber(laundryRequestDto.getMobileNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.MOBILE_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (userRepository.existsByEmail(laundryRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		Laundry laundry = customMapper.laundryRequestDtoTolandry(laundryRequestDto);

		laundry.setCreatedAt(new Date());
		laundry.setLaundryStatus(true);

		Admin admin = new Admin();
		String password = Utility.generateRandomPassword(8);
		System.out.println(password);
		String newPasswordEncodedString = bCryptPasswordEncoder.encode(password);
		laundry.setPassword(newPasswordEncodedString);
		saveLaundry(laundry);
		admin.setPassword(newPasswordEncodedString);
		admin.setCreatedAt(new Date());
		admin.setRole(1);
		admin.setEmail(laundryRequestDto.getEmail());
		admin.setLaundryid(laundry.getId());
		saveAdmin(admin);
		apiResponseDtoBuilder.withMessage(Constants.USER_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(laundry);
		verificationTokenService.sendWelcomeToken(password, laundryRequestDto.getEmail());

		apiResponseDtoBuilder.withMessage("Laundry Add Sucessfully").withStatus(HttpStatus.OK).withData(laundry);

	}

	private void saveLaundry(Laundry laundry) {
		laundryRepository.save(laundry);
	}

	private void saveAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	@Override
	public void updateLaundry(@Valid Laundry laundry, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Laundry> laundryCheck = laundryRepository.findById(laundry.getId());
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser == null || currentUser.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			return;
		}
		if (!laundryCheck.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.NO_LAUNDRY_EXISTS).withStatus(HttpStatus.OK);
			return;
		}
		laundry.setUpdatedAt(new Date());
		laundryRepository.save(laundry);
		apiResponseDtoBuilder.withMessage("Laundry  Update Successfully.").withStatus(HttpStatus.OK).withData(laundry);

	}

	@Override
	public void getAllLaundryDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
//		User currentUser = Utility.getSessionUser(userRepository);
//		if (currentUser == null || currentUser.getRole() != 0) {
//			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
//			return;
//		}
		List<Laundry> cabList = laundryRepository.findAll();
		apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(cabList);

	}

	@Override
	public void getLaundryDetailsByUserId(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Laundry> user = laundryRepository.findById(id);
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser == null || currentUser.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			return;
		}
		if (user.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(user);
		} else {
			apiResponseDtoBuilder.withMessage("data not fond.").withStatus(HttpStatus.OK);
		}
	}

	@Override
	public void deleteLaundryById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Laundry> user = laundryRepository.findById(id);
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser == null || currentUser.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			return;
		}
		if (user.isPresent()) {
			laundryRepository.deleteById(id);
			Admin admin = adminRepository.findByLaundryid(id);
			admin.setActive(false);
			adminRepository.save(admin);
			serviceRepository.deleteBylaundryId(id);
			priceListRepository.deleteByLaundryId(id);
			orderRepository.deleteByLaundryId(id);
			apiResponseDtoBuilder.withMessage("Laundry Deleted Successfully").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void isActiveLaundry(long id, boolean active, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User currentUser = Utility.getSessionUser(userRepository);
		if (currentUser == null || currentUser.getRole() != 0) {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			return;
		}
		Optional<Laundry> laundry = laundryRepository.findById(id);
		if (laundry.isPresent()) {
			laundry.get().setLaundryStatus(active);
			save(laundry.get());
			apiResponseDtoBuilder.withMessage("Laundry Status Change successfully..").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.LAUNDRY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	private void save(Laundry laundry) {
		laundryRepository.save(laundry);
	}

	@Override
	public void ChangeOOStatus(long id, int status, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Laundry> laundry = laundryRepository.findById(id);
		if (laundry.isPresent()) {
			save(laundry.get());
			apiResponseDtoBuilder
					.withMessage(status == 1 ? Constants.LAUNDRY_APPROVE_SUCCESS : Constants.LAUNDRY_REJECT_SUCCESS)
					.withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.LAUNDRY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void orderConfirmation(long id, int status, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Laundry> laundry = laundryRepository.findById(id);

		if (laundry.isPresent()) {
			save(laundry.get());
			apiResponseDtoBuilder
					.withMessage(status == 1 ? Constants.LAUNDRY_APPROVE_SUCCESS : Constants.LAUNDRY_REJECT_SUCCESS)
					.withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.LAUNDRY_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}

}
