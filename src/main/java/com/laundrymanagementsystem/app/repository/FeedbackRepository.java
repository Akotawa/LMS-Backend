package com.laundrymanagementsystem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	List<Feedback> findByLaundryId(long driverId);

	List<Feedback> findByCustomerId(long customerId);

	Feedback findByOrderId(Long id);

	boolean existsByCustomerId(Long id);

}
