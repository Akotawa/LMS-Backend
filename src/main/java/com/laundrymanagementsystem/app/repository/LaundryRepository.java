package com.laundrymanagementsystem.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Laundry;

@Repository
public interface LaundryRepository extends JpaRepository<Laundry, Long> {

	boolean existsByMobileNumber(String mobileNumber);

	Laundry findByMobileNumber(String mobileNumber);

	 

}
