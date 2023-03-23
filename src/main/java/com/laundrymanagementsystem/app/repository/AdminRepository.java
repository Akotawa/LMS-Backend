package com.laundrymanagementsystem.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.SuperAdmin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByMobileNumberOrEmail(String username, String username2);

	Admin findByEmail(String username);


	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmail(String email);


}
