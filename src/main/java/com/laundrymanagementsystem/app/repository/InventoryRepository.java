package com.laundrymanagementsystem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrymanagementsystem.app.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	List<Inventory> findByUsedItemGreaterThan(long l);

}
