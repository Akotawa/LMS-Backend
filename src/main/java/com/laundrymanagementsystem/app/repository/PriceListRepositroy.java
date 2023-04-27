package com.laundrymanagementsystem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.PriceList;

@Repository
public interface PriceListRepositroy extends JpaRepository<PriceList, Long> {

	List<PriceList> findAllByLaundryId(long id);

	boolean existsByLaundryId(long laundryId);

	PriceList findByLaundryId(long laundryId);
}
