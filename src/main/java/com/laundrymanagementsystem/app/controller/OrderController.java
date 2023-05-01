package com.laundrymanagementsystem.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.OrderRequestDto;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.service.IOrderService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class OrderController {

	@Autowired
	IOrderService orderService;

	@PostMapping(value = "order/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.addOrder(orderRequestDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PutMapping(value = "order/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateOrder(@Valid @RequestBody Order order) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.updateOrder(order, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "getAll/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllOrder() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getAllOrder(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "get/order/{orderID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getOrderById(@PathVariable(required = true) Long orderID) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getOrderById(apiResponseDtoBuilder, orderID);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "get/orders/{laundryID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getOrdersByLaundryID(@PathVariable(required = true) Long laundryID) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getOrdersByLaundryID(apiResponseDtoBuilder, laundryID);
		return apiResponseDtoBuilder.build();
	}

	@DeleteMapping(value = "order/{id}/delete")
	public ApiResponseDto deleteOrderById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.deleteOrderById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/order/{id}/{status}/statusUpdate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateOrderStatus(@PathVariable(required = true) long id,
			@PathVariable(required = true) int status) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.updateOrderStatus(id, status, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/cancel/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto cancelOrder(@PathVariable(required = true) Long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.cancelOrder(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PutMapping(value = "orderPaymentStatus/update/{id}/{paymentStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateOrderPaymentStatusById(@PathVariable(required = true) long id,
			@PathVariable(required = true) boolean paymentStatus) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.updateOrderPaymentStatusById(id, paymentStatus, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/order/invoice/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getOrderInvoiceByOrderId(@PathVariable(required = true) long orderId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		orderService.getOrderInvoice(apiResponseDtoBuilder, orderId);
		return apiResponseDtoBuilder.build();
	}
}
