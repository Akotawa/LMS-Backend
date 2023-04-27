package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.PriceListRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.PriceList;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.LaundryRepository;
import com.laundrymanagementsystem.app.repository.PriceListRepositroy;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.service.IPriceListService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service
public class PriceListServiceImpl implements IPriceListService {

	@Autowired
	LaundryRepository laundryRepository;

	@Autowired
	PriceListRepositroy priceListRepositroy;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CustomMapper customMapper;

	@Override
	public void addPrice(ApiResponseDtoBuilder apiResponseDtoBuilder, PriceListRequestDto priceListRequestDto) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() == 1 || sessionUser.getRole() == 2) {
			PriceList priceList = customMapper.PriceListRequestDtoToPriceList(priceListRequestDto);
			priceList.setCreatedAt(new Date());
			priceListRepositroy.save(priceList);
			apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage("price added successfully").withData(priceList);
		} else {
			apiResponseDtoBuilder.withStatus(HttpStatus.UNAUTHORIZED).withMessage(Constants.UNAUTHORIZED);
		}
	}

	@Override
	public void getPrice(ApiResponseDtoBuilder apiResponseDtoBuilder, long id) {
		if (Utility.getSessionUser(userRepository).getRole() == 1
				|| Utility.getSessionUser(userRepository).getRole() == 2) {
			if (priceListRepositroy.existsByLaundryId(id)) {
				List<PriceList> priceList = priceListRepositroy.findAllByLaundryId(id);
				apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(priceList);
			} else {
				apiResponseDtoBuilder.withStatus(HttpStatus.NOT_FOUND).withMessage("price not found");
			}
		} else
			apiResponseDtoBuilder.withStatus(HttpStatus.UNAUTHORIZED).withMessage(Constants.UNAUTHORIZED);
	}
}
