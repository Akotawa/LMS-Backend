package com.laundrymanagementsystem.app.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.OrderRequestDto;
import com.laundrymanagementsystem.app.model.Order;

@Service
public interface IOrderService {

	void addOrder(@Valid OrderRequestDto orderRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateOrder(@Valid Order order, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllOrder(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deleteOrderById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateOrderStatus(long id, int status, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateOrderPaymentStatusById(long id, boolean paymentStatus, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getOrderInvoice(ApiResponseDtoBuilder apiResponseDtoBuilder, long orderId);

	void getOrdersByLaundryID(ApiResponseDtoBuilder apiResponseDtoBuilder, Long laundryID);

	void getOrderById(ApiResponseDtoBuilder apiResponseDtoBuilder, Long orderID);

	void cancelOrder(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
