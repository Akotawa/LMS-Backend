package com.laundrymanagementsystem.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.OrderRequestDto;
import com.laundrymanagementsystem.app.model.Order;
@Service
public interface IOrderService {

	void addOrder(  @Valid OrderRequestDto orderRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	void updateOrder(   @Valid Order order, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllOrder(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deleteOrderById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateOrderStatus(long id ,String status, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
