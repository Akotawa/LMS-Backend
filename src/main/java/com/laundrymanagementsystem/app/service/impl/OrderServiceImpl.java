package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.OrderRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.service.IOrderService;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomMapper customMapper;
	@Autowired
	private IVerificationTokenService verificationTokenService;

	@Override
	public void addOrder(@Valid OrderRequestDto orderRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder, HttpServletRequest request) {
		if (orderRepository.existsByContactNumber(orderRequestDto.getContactNumber())) {
			apiResponseDtoBuilder.withMessage(Constants.CONTACT_NUMBER_ALREADY_EXISTS)
					.withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
		if (orderRepository.existsByEmail(orderRequestDto.getEmail())) {
			apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS).withStatus(HttpStatus.ALREADY_REPORTED);
			return;
		}
	Order order = customMapper.orderRequestDtoToOrder(orderRequestDto);
		order.setReceivedDate(new Date());
		order.setCompletionDate(new Date());
		saveOrder(order);
		apiResponseDtoBuilder.withMessage("Order Add Sucessfully").withStatus(HttpStatus.OK).withData(order);
		verificationTokenService.sendVerificationToken(order);
	}

	private void saveOrder(Order order) {
		orderRepository.save(order);

	}

	@Override
	public void updateOrder(@Valid Order order, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Order> orderCheck = orderRepository.findById(order.getId());
		if (!orderCheck.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.OK);
			return;
		}
		orderRepository.save(order);
		apiResponseDtoBuilder.withMessage("Order Details Update Successfully.").withStatus(HttpStatus.OK)
				.withData(order);
	}

	@Override
	public void getAllOrder(ApiResponseDtoBuilder apiResponseDtoBuilder) {

		List<Order> orderList = orderRepository.findAll();
		 apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(orderList);
	}

	@Override
	public void deleteOrderById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {

		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()) {
			orderRepository.deleteById(id);
			apiResponseDtoBuilder.withMessage("Order Deleted Successfully").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void updateOrderStatus(long id ,String status, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Order> orderCheck = orderRepository.findById(id);
		if (!orderCheck.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.OK);
			return;
		}
		else {
			orderCheck.get().setOrderStatus(status);
		orderRepository.save(orderCheck.get());
		apiResponseDtoBuilder.withMessage("Order Status Update Successfully.").withStatus(HttpStatus.OK);
		verificationTokenService.sendBookingToken(orderCheck.get());
	}
		
	}

}
