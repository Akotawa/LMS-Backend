package com.laundrymanagementsystem.app.repository;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laundrymanagementsystem.app.dto.OrderRequestDto;
import com.laundrymanagementsystem.app.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	void save(@Valid OrderRequestDto orderRequestDto);

	boolean existsByContactNumber(String contactNumber);

	boolean existsByEmail(String email);

}
