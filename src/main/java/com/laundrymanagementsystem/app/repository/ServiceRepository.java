package com.laundrymanagementsystem.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Services;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {

	Optional<Services> findByLaundryId(long laundryId);

}
