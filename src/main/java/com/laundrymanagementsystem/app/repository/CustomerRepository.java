package com.laundrymanagementsystem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByRole(int role);

	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmail(String email);

	Customer findByEmail(String email);


}
