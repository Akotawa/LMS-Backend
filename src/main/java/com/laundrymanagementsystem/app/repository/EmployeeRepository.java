package com.laundrymanagementsystem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByRole(int role);

	boolean existsByMobileNumber(String mobileNumber);

	Employee findByMobileNumber(String mobileNumber);

	Employee findByEmail(String email);


	long countByLaundryid(Long laundryid);

}
