package com.laundrymanagementsystem.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	IOrderService iOrderService;

	@PostMapping(value = "/order/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addOrder(@Valid @RequestBody OrderRequestDto orderRequestDto,
			final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iOrderService.addOrder(orderRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/order/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateOrder(@Valid @RequestBody Order order) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iOrderService.updateOrder(order, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllOrder() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iOrderService.getAllOrder(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@DeleteMapping(value = "/order/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto deleteOrderById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iOrderService.deleteOrderById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/order/{id}/{status}/statusUpdate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateOrderStatus(@PathVariable(required = true) long id, String status) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iOrderService.updateOrderStatus(id, status, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

}
