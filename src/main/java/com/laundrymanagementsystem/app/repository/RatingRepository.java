package com.laundrymanagementsystem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByOrderId(long orderId);

	List<Rating> findByCustumerId(long custumerId);

	Rating findByOrderId(Long id);

	boolean existsByCustumerId(Long custumerId);

}
