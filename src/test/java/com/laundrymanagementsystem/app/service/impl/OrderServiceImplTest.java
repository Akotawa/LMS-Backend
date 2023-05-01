package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import com.laundrymanagementsystem.app.dto.OrderRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Feedback;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.Rating;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.FeedbackRepository;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.RatingRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;
import com.laundrymanagementsystem.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
	@InjectMocks
	OrderServiceImpl orderServiceImpl;
	@Mock
	InventoryRepository inventoryRepository;
	@Mock
	OrderRepository orderRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	IVerificationTokenService verificationTokenService;
	@Mock
	FeedbackRepository feedbackRepository;
	@Mock
	RatingRepository ratingRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addOrder() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderRequestDto.setLaundryId(1L);

		Inventory inventory = new Inventory();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Order order = new Order();
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		orderServiceImpl.addOrder(orderRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Order Add Sucessfully"));

	}

	@Test
	public void updateOrder() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Order orderRequestDto = new Order();
		orderRequestDto.setLaundryId(1L);
		orderRequestDto.setPaymentStatus(true);

		Inventory inventory = new Inventory();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Order laundrytest = new Order();
		Optional<Order> user = Optional.ofNullable(laundrytest);
		when(orderRepository.findById(orderRequestDto.getId())).thenReturn(user);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		orderServiceImpl.updateOrder(orderRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Order Details Update Successfully."));

	}

	@Test
	public void getAllOrder() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Order order = new Order();
		order.setId(1l);
		List<Order> orderList = new ArrayList<>();
		orderList.add(order);
		when(orderRepository.findAll()).thenReturn(orderList);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		orderServiceImpl.getAllOrder(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Success"));
	}

	@Test
	public void deleteOrderById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		Order Orders = new Order();
		Orders.setId(1l);
		Optional<Order> order = Optional.ofNullable(Orders);
		when(orderRepository.findById(1l)).thenReturn(order);
		orderServiceImpl.deleteOrderById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Order Deleted Successfully"));
	}

	@Test
	public void updateOrderStatus() {
		long id = 1l;
		int status = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Order laundrytest = new Order();
		laundrytest.setId(1l);
		Optional<Order> user = Optional.ofNullable(laundrytest);
		when(orderRepository.findById(sessionUser.getId())).thenReturn(user);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		orderServiceImpl.updateOrderStatus(id, status, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Order Status Update Successfully."));
	}

	@Test
	public void updateOrderPaymentStatusById() {
		long id = 1l;
		Boolean paymentStatus = true;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(3);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		Order laundrytest = new Order();
		laundrytest.setId(1l);
		Optional<Order> user = Optional.ofNullable(laundrytest);
		when(orderRepository.findById(sessionUser.getId())).thenReturn(user);
		
		orderServiceImpl.updateOrderPaymentStatusById(id, paymentStatus, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Order Payment Status Update Successfully..."));
	}

	@Test
	public void getOrderInvoice() {
		long orderId = 1l;
		int status = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Order Order = new Order();
		Optional<Order> order = Optional.ofNullable(Order);
		when(orderRepository.findById(1l)).thenReturn(order);
		orderServiceImpl.getOrderInvoice(apiResponseDtoBuilder, orderId);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Success"));

	}

	@Test
	public void getOrdersByLaundryID() {
		long laundryID = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		Order orders = new Order();
		orders.setId(1l);
		List<Order> listOfOrder = new ArrayList<>();
		listOfOrder.add(orders);
		when(orderRepository.findByLaundryId(laundryID)).thenReturn(listOfOrder);
		orderServiceImpl.getOrdersByLaundryID(apiResponseDtoBuilder, laundryID);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Success"));

	}

	@Test
	public void getOrderById() {
		long orderID = 1l;
		int status = 1;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Order Orders = new Order();
		Orders.setId(1l);
		Optional<Order> order = Optional.ofNullable(Orders);
		when(orderRepository.findById(1l)).thenReturn(order);
		orderServiceImpl.getOrderById(apiResponseDtoBuilder, orderID);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Success"));

	}
}
