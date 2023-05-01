package com.laundrymanagementsystem.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.LaundryMachineAssignmentDto;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.OrderRepository;
import com.laundrymanagementsystem.app.service.ILaundryMachineAssignment;

@Service
public class LaundryMachineAssignmentServiceImpl implements ILaundryMachineAssignment {

	@Autowired
	InventoryRepository inventoryRepository;

	@Autowired
	OrderRepository orderRepository;

	@Override
	public void addLaundryMachineAssign(ApiResponseDtoBuilder apiResponseDtoBuilder,
			LaundryMachineAssignmentDto laundryMachineAssignmentDto) {
		Optional<Order> order = orderRepository.findById(laundryMachineAssignmentDto.getOrderID());
		Optional<Inventory> inventory = inventoryRepository
				.findById(laundryMachineAssignmentDto.getLaundryMachineAssignmentId());
		if (inventory.isPresent() && (order.isPresent())) {
			order.get().setLaundryMachineId(laundryMachineAssignmentDto.getLaundryMachineAssignmentId());
			orderRepository.save(order.get());
			inventory.get().setUsedItem(inventory.get().getUsedItem() - 1);
			inventoryRepository.save(inventory.get());
			apiResponseDtoBuilder.withStatus(HttpStatus.OK).withMessage("Machine Assigned");
		} else {
			apiResponseDtoBuilder.withStatus(HttpStatus.NOT_FOUND).withMessage("Not found");
		}
	}

}
