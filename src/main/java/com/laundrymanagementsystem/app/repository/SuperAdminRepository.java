package com.laundrymanagementsystem.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.SuperAdmin;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {

	SuperAdmin findByMobileNumberOrEmail(String username, String username2);

	SuperAdmin findByEmail(String username);

}
