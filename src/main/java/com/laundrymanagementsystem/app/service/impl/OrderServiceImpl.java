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
import com.laundrymanagementsystem.app.dto.OrderTrackResponse;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.FeedbackRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.RatingRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.service.IOrderService;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomMapper customMapper;
	@Autowired
	private IVerificationTokenService verificationTokenService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public void addOrder(@Valid OrderRequestDto orderRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() == 2 || sessionUser.getRole() == 3 && sessionUser != null) {
			if (orderRepository.existsByContactNumber(orderRequestDto.getContactNumber())) {
				apiResponseDtoBuilder.withMessage(Constants.CONTACT_NUMBER_ALREADY_EXISTS)
						.withStatus(HttpStatus.ALREADY_REPORTED);
				return;
			}
			if (orderRepository.existsByEmail(orderRequestDto.getEmail())) {
				apiResponseDtoBuilder.withMessage(Constants.EMAIL_ALREADY_EXISTS)
						.withStatus(HttpStatus.ALREADY_REPORTED);
				return;
			}
			Order order = customMapper.orderRequestDtoToOrder(orderRequestDto);
			order.setReceivedDate(new Date());
			order.setCompletionDate(new Date());
			order.setOrderStatus(0);
			saveOrder(order);
			apiResponseDtoBuilder.withMessage("Order Add Sucessfully").withStatus(HttpStatus.OK).withData(order);
			verificationTokenService.sendBookingToken(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	private void saveOrder(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void updateOrder(@Valid Order order, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() == 2 && sessionUser != null) {
			Optional<Order> orderCheck = orderRepository.findById(order.getId());
			if (!orderCheck.isPresent()) {
				apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.NOT_FOUND);
				return;
			}
			order.setUpdatedAt(new Date());
			orderRepository.save(order);
			apiResponseDtoBuilder.withMessage("Order Details Update Successfully.").withStatus(HttpStatus.OK)
					.withData(order);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getAllOrder(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() == 2 && sessionUser != null) {
			List<Order> orderList = orderRepository.findAll();
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(orderList);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void deleteOrderById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {

		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() == 2 && sessionUser != null) {
			Optional<Order> order = orderRepository.findById(id);
			if (order.isPresent()) {
				orderRepository.deleteById(id);
				apiResponseDtoBuilder.withMessage("Order Deleted Successfully").withStatus(HttpStatus.OK);
			} else {
				apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void updateOrderStatus(long id, int status, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() == 2 && sessionUser != null) {
			Optional<Order> orderCheck = orderRepository.findById(id);
			if (!orderCheck.isPresent()) {
				apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.NOT_FOUND);
				return;
			} else {
				orderCheck.get().setOrderStatus(status);
				orderRepository.save(orderCheck.get());
				apiResponseDtoBuilder.withMessage("Order Status Update Successfully.").withStatus(HttpStatus.OK);
				verificationTokenService.sendBookingToken(orderCheck.get());
				if (status == 2) {
					getOrderInvoice(apiResponseDtoBuilder, orderCheck.get().getId());
				}
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void updateOrderPaymentStatusById(long id, boolean paymentStatus,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if ((sessionUser.getRole() == 3 || sessionUser.getRole() == 2) && sessionUser != null) {
			Optional<Order> order = orderRepository.findById(id);
			if (order.isPresent()) {
				order.get().setPaymentStatus(paymentStatus);
				orderRepository.save(order.get());
				apiResponseDtoBuilder.withMessage("Order Payment Status Update Successfully...")
						.withStatus(HttpStatus.OK).withData(order);
			} else {
				apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.NOT_FOUND);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getOrderInvoice(ApiResponseDtoBuilder apiResponseDtoBuilder, long orderId) {
		Optional<Order> order = orderRepository.findById(orderId);
		if (order.isPresent()) {
			verificationTokenService.sendBill(order.get());
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK);
		} else
			apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.NOT_FOUND);
	}

	@Override
	public void getOrdersByLaundryID(ApiResponseDtoBuilder apiResponseDtoBuilder, Long laundryID) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() != 3 && sessionUser != null) {
			List<Order> orderList = orderRepository.findByLaundryId(laundryID);
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(orderList);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getOrderById(ApiResponseDtoBuilder apiResponseDtoBuilder, Long orderID) {
		Optional<Order> order = orderRepository.findById(orderID);
		if (order.isPresent()) {
			OrderTrackResponse response = new OrderTrackResponse();
			response.setOrder(order.get());
			response.setFeedback(feedbackRepository.findByOrderId(order.get().getId()));
			response.setRating(ratingRepository.findByOrderId(order.get().getId()));
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(response);
		} else
			apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.NOT_FOUND);

	}
}
