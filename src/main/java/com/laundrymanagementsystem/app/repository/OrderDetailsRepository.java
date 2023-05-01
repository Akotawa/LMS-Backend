package com.laundrymanagementsystem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laundrymanagementsystem.app.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

	List<OrderDetails> findByOrderId(Long id);

}
