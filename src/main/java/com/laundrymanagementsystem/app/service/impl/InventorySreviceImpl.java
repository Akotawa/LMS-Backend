package com.laundrymanagementsystem.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.InventoryRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.service.IInventoryService;

@Service
public class InventorySreviceImpl implements IInventoryService {

	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	CustomMapper customMapper;


	@Override
	public void addItem(@Valid @RequestBody(required = true) InventoryRequestDto inventoryRequestDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder, HttpServletRequest request) {
		Inventory inventory = customMapper.inventoryRequestDtoToInventory(inventoryRequestDto);
		saveItem(inventory);
		apiResponseDtoBuilder.withMessage("Inventory Add Sucessfully").withStatus(HttpStatus.OK).withData(inventory);
	}

	private void saveItem(Inventory inventory) {
		inventoryRepository.save(inventory);
	}

	@Override
	public void getAllItem(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Inventory> itemList = inventoryRepository.findAll();
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(itemList);
		
	}

	@Override
	public void deleteItemById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Inventory> item = inventoryRepository.findById(id);
		if (item.isPresent()) {
			inventoryRepository.deleteById(id);

			apiResponseDtoBuilder.withMessage("Inventory Deleted Successfully").withStatus(HttpStatus.OK);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}
		
	}

	@Override
	public void updateItem(@Valid Inventory inventory, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Inventory> inventoryChaeck = inventoryRepository.findById(inventory.getId());
		if(inventoryChaeck.isPresent()) {
			inventoryRepository.save(inventory);
			apiResponseDtoBuilder.withMessage("Inventory Update Successfully.").withStatus(HttpStatus.OK)
					.withData(inventory);
		}else {
			apiResponseDtoBuilder.withMessage(Constants.NO_ITEM_EXISTS).withStatus(HttpStatus.OK);
			return;
		}
		
	}
}
