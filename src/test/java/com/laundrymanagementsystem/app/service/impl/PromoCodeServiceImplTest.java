package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.PromoCodeRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.PromoCode;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.PromoCodeRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class PromoCodeServiceImplTest {
	@InjectMocks
	PromoCodeServiceImpl promoCodeServiceImpl;
	@Mock
	InventoryRepository inventoryRepository;
	@Mock
	PromoCodeRepository promoCodeRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addPromoCode() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PromoCodeRequestDto promoCodeRequestDto = new PromoCodeRequestDto();
		promoCodeRequestDto.setCode("test");
		promoCodeRequestDto.setDescription("test");
		promoCodeRequestDto.setDiscount("5");
		promoCodeRequestDto.setEndDate(new Date());
		promoCodeRequestDto.setStartDate(new Date());
		PromoCode promoCode = new PromoCode();
		when(customMapper.promoCodeRequestDtoToPromoCode(promoCodeRequestDto)).thenReturn(promoCode);
		promoCodeServiceImpl.addPromoCode(promoCodeRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Promo code added successfully"));

	}

	@Test
	public void getPromoCodeById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();

		PromoCode promoCode = new PromoCode();
		promoCode.setId(1l);
		Optional<PromoCode> promoCodes = Optional.ofNullable(promoCode);
		when(promoCodeRepository.findById(id)).thenReturn(promoCodes);
		promoCodeServiceImpl.getPromoCodeById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.PROMO_CODE_INFO));

	}

	@Test
	public void getAllPromoCode() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PromoCodeRequestDto promoCodeRequestDto = new PromoCodeRequestDto();
		promoCodeRequestDto.setCode("test");
		promoCodeRequestDto.setDescription("test");
		promoCodeRequestDto.setDiscount("5");
		promoCodeRequestDto.setEndDate(new Date());
		promoCodeRequestDto.setStartDate(new Date());
		PromoCode promoCode = new PromoCode();
		List<PromoCode> listOfPromoCode = null;
		when(promoCodeRepository.findAll()).thenReturn(listOfPromoCode);
		promoCodeServiceImpl.getAllPromoCode(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Promo code list"));

	}

	@Test
	public void isPromocodeValid() {
		String promoCode = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PromoCodeRequestDto promoCodeRequestDto = new PromoCodeRequestDto();
		promoCodeRequestDto.setCode("test");
		promoCodeRequestDto.setDescription("test");
		promoCodeRequestDto.setDiscount("5");
		promoCodeRequestDto.setEndDate(new Date());
		promoCodeRequestDto.setStartDate(new Date());
		PromoCode promoCodeResult = new PromoCode();
		when(promoCodeRepository.findByCode(promoCode)).thenReturn(promoCodeResult);
		promoCodeServiceImpl.isPromocodeValid(promoCode, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Promo code info"));

	}

	@Test
	public void deletePromoCodeById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		PromoCodeRequestDto promoCodeRequestDto = new PromoCodeRequestDto();
		promoCodeRequestDto.setCode("test");
		promoCodeRequestDto.setDescription("test");
		promoCodeRequestDto.setDiscount("5");
		promoCodeRequestDto.setEndDate(new Date());
		promoCodeRequestDto.setStartDate(new Date());
		PromoCode promoCode = new PromoCode();
		promoCode.setId(1l);
		Optional<PromoCode> promoCodes = Optional.ofNullable(promoCode);
		when(promoCodeRepository.findById(id)).thenReturn(promoCodes);
		promoCodeServiceImpl.deletePromoCodeById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Order Status Update Successfully."));

	}
}
