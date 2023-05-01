package com.laundrymanagementsystem.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.OrderDetailsResponseDto;
import com.laundrymanagementsystem.app.dto.OrderRequestDto;
import com.laundrymanagementsystem.app.dto.OrderResponseDto;
import com.laundrymanagementsystem.app.dto.OrderTrackResponse;
import com.laundrymanagementsystem.app.dto.ServiceWIthQuantity;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.OrderDetails;
import com.laundrymanagementsystem.app.model.Services;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.CustomerRepository;
import com.laundrymanagementsystem.app.repository.FeedbackRepository;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.OrderDetailsRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.RatingRepository;
import com.laundrymanagementsystem.app.repository.ServiceRepository;
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

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public void addOrder(@Valid OrderRequestDto orderRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser != null && (sessionUser.getRole() == 2 || sessionUser.getRole() == 3)) {
			Order order = new Order();
			order.setCustomerId(orderRequestDto.getCustomerId());
			order.setLaundryId(orderRequestDto.getLaundryId());
			order.setReceivedDate(new Date());
			order.setCompletionDate(new Date());
			order.setOrderStatus(0);
			order.setCreatedAt(new Date());
			order.setPaymentStatus(false);
			order.setOrderId(UUID.randomUUID().toString());
			saveOrder(order);
			for (ServiceWIthQuantity serviceWIthQuantity : orderRequestDto.getServiceIdWithQuantity()) {
				OrderDetails orderDetails = new OrderDetails();
				orderDetails.setOrderId(order.getId());
				orderDetails.setServiceId(serviceWIthQuantity.getServiceId());
				orderDetails.setQuantity(serviceWIthQuantity.getQuantity());
				orderDetails.setPayment(serviceWIthQuantity.getPayment());
				orderDetailsRepository.save(orderDetails);
			}
			Optional<User> user = userRepository.findById(order.getCustomerId());
			OrderResponseDto orderResponseDto = customMapper.orderToOrderResponseDto(order);
			orderResponseDto.setFullName(user.get().getFullName());
			orderResponseDto.setEmail(user.get().getEmail());
			orderResponseDto.setMobileNumber(user.get().getMobileNumber());
			apiResponseDtoBuilder.withMessage("Order Add Sucessfully").withStatus(HttpStatus.OK)
					.withData(orderResponseDto);
			verificationTokenService.sendBookingToken(sessionUser);
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
			List<OrderResponseDto> orders = new ArrayList<>();
			for (Order order : orderList) {
				Optional<User> user = userRepository.findById(order.getCustomerId());
				OrderResponseDto orderResponseDto = customMapper.orderToOrderResponseDto(order);
				orderResponseDto.setFullName(user.get().getFullName());
				orderResponseDto.setEmail(user.get().getEmail());
				orderResponseDto.setMobileNumber(user.get().getMobileNumber());
				List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrderId(order.getId());
				List<OrderDetailsResponseDto> orderDetails = new ArrayList<>();
				for (OrderDetails orderDetail : orderDetailsList) {
					OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto();
					Optional<Services> services = serviceRepository.findById(orderDetail.getServiceId());
					orderDetailsResponseDto.setServiceName(services.isPresent() ? services.get().getServiceName() : "");
					orderDetailsResponseDto.setQuantity(orderDetail.getQuantity());
					orderDetailsResponseDto.setPayment(orderDetail.getPayment());
					orderDetails.add(orderDetailsResponseDto);
				}
				if (order.getLaundryMachineId() != null) {
					Optional<Inventory> inventory = inventoryRepository.findById(order.getLaundryMachineId());
					orderResponseDto.setMachineName(inventory.isPresent() ? inventory.get().getItemName() : "");
				}
				orderResponseDto.setDetails(orderDetails);
				orders.add(orderResponseDto);
			}
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(orders);
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
				verificationTokenService.sendBookingToken(sessionUser);
				if (status == 2) {
					getOrderInvoice(apiResponseDtoBuilder, orderCheck.get().getId());
				}
				if (status == 2 && orderCheck.get().getLaundryMachineId() != null) {
					Optional<Inventory> inventory = inventoryRepository
							.findById(orderCheck.get().getLaundryMachineId());
					if (inventory.isPresent()) {
						inventory.get().setUsedItem(inventory.get().getUsedItem() - 1);
						inventoryRepository.save(inventory.get());
					}
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
			Optional<User> user = userRepository.findById(order.get().getCustomerId());
			verificationTokenService.sendBill(user.get(), order.get());
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK);
		} else
			apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.NOT_FOUND);
	}

	@Override
	public void getOrdersByLaundryID(ApiResponseDtoBuilder apiResponseDtoBuilder, Long laundryID) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if (sessionUser.getRole() != 3 && sessionUser != null) {
			List<Order> orderList = orderRepository.findByLaundryId(laundryID);
			List<OrderResponseDto> orders = new ArrayList<>();
			for (Order order : orderList) {
				Optional<User> user = userRepository.findById(order.getCustomerId());
				OrderResponseDto orderResponseDto = customMapper.orderToOrderResponseDto(order);
				orderResponseDto.setFullName(user.get().getFullName());
				orderResponseDto.setEmail(user.get().getEmail());
				orderResponseDto.setMobileNumber(user.get().getMobileNumber());
				List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrderId(order.getId());
				List<OrderDetailsResponseDto> orderDetails = new ArrayList<>();
				for (OrderDetails orderDetail : orderDetailsList) {
					OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto();
					Optional<Services> services = serviceRepository.findById(orderDetail.getServiceId());
					orderDetailsResponseDto.setServiceName(services.isPresent() ? services.get().getServiceName() : "");
					orderDetailsResponseDto.setQuantity(orderDetail.getQuantity());
					orderDetailsResponseDto.setPayment(orderDetail.getPayment());
					orderDetails.add(orderDetailsResponseDto);
				}
				orderResponseDto.setDetails(orderDetails);
				orders.add(orderResponseDto);
			}
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(orders);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getOrderById(ApiResponseDtoBuilder apiResponseDtoBuilder, Long orderID) {
		Optional<Order> order = orderRepository.findById(orderID);
		if (order.isPresent()) {
			OrderTrackResponse response = new OrderTrackResponse();
			Optional<User> user = userRepository.findById(order.get().getCustomerId());
			OrderResponseDto orderResponseDto = customMapper.orderToOrderResponseDto(order.get());
			orderResponseDto.setFullName(user.get().getFullName());
			orderResponseDto.setEmail(user.get().getEmail());
			orderResponseDto.setMobileNumber(user.get().getMobileNumber());
			response.setOrderResponseDto(orderResponseDto);
			response.setFeedback(feedbackRepository.findByOrderId(order.get().getId()));
			response.setRating(ratingRepository.findByOrderId(order.get().getId()));
			List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrderId(orderID);
			List<OrderDetailsResponseDto> orderDetails = new ArrayList<>();
			for (OrderDetails orderDetail : orderDetailsList) {
				OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto();
				Optional<Services> services = serviceRepository.findById(orderDetail.getServiceId());
				orderDetailsResponseDto.setServiceName(services.isPresent() ? services.get().getServiceName() : "");
				orderDetailsResponseDto.setQuantity(orderDetail.getQuantity());
				orderDetailsResponseDto.setPayment(orderDetail.getPayment());
				orderDetails.add(orderDetailsResponseDto);
			}
			orderResponseDto.setDetails(orderDetails);
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(response);
		} else
			apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.NOT_FOUND);

	}

	@Override
	public void cancelOrder(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Order> orderCheck = orderRepository.findById(id);
		if (!orderCheck.isPresent()) {
			apiResponseDtoBuilder.withMessage(Constants.NO_ORDER_EXISTS).withStatus(HttpStatus.NOT_FOUND);
			return;
		} else {
			orderCheck.get().setOrderStatus(4);
			orderRepository.save(orderCheck.get());
			apiResponseDtoBuilder.withMessage("Order Canceled Successfully.").withStatus(HttpStatus.OK);
		}

	}
}
