package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LaundryMachineAssignmentDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.repository.PromoCodeRepository;

@ExtendWith(MockitoExtension.class)
public class LaundryMachineAssignmentServiceImplTest {
	@InjectMocks
	LaundryMachineAssignmentServiceImpl laundryMachineAssignmentServiceImpl;
	@Mock
	InventoryRepository inventoryRepository;
	@Mock
	OrderRepository orderRepository;
	@Mock
	PromoCodeRepository promoCodeRepository;
	@Mock
	CustomMapper customMapper;

	@Test
	public void addLaundryMachineAssign() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		LaundryMachineAssignmentDto laundryMachineAssignmentDto = new LaundryMachineAssignmentDto();
		laundryMachineAssignmentDto.setLaundryMachineAssignmentId(1l);
		laundryMachineAssignmentDto.setOrderID(1l);
		Order order = new Order();
		order.setId(1l);
		Optional<Order> orderss = Optional.ofNullable(order);
		when(orderRepository.findById(1l)).thenReturn(orderss);
		Inventory inventory = new Inventory();
		inventory.setId(1l);
		Optional<Inventory> test = Optional.ofNullable(inventory);
		when(inventoryRepository.findById(laundryMachineAssignmentDto.getLaundryMachineAssignmentId()))
				.thenReturn(test);
		laundryMachineAssignmentServiceImpl.addLaundryMachineAssign(apiResponseDtoBuilder, laundryMachineAssignmentDto);
		//assertTrue(apiResponseDtoBuilder.getMessage().equals("null"));

	}
}
