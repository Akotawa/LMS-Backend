package com.laundrymanagementsystem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Services;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {

	List<Services> findAllByLaundryId(long laundryId);

	long countByLaundryId(Long laundryid);

	void deleteBylaundryId(long id);

}
