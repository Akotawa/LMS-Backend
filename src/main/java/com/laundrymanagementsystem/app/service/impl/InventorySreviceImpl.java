package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;
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
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.service.IInventoryService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service
public class InventorySreviceImpl implements IInventoryService {

	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	CustomMapper customMapper;
	@Autowired
	UserRepository userRepository;

	@Override
	public void addItem(@Valid @RequestBody(required = true) InventoryRequestDto inventoryRequestDto,
			ApiResponseDtoBuilder apiResponseDtoBuilder, HttpServletRequest request) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if ((sessionUser.getRole() == 2 && sessionUser != null)||(sessionUser.getRole() == 1 && sessionUser != null)) {
			Inventory inventory = customMapper.inventoryRequestDtoToInventory(inventoryRequestDto);
			inventory.setCreatedAt(new Date());
			saveItem(inventory);
			apiResponseDtoBuilder.withMessage(Constants.ITEM_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(inventory);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	private void saveItem(Inventory inventory) {
		inventoryRepository.save(inventory);
	}

	@Override
	public void getAllItem(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if ((sessionUser.getRole() == 2 && sessionUser != null)||(sessionUser.getRole() == 1 && sessionUser != null)) {
			List<Inventory> itemList = inventoryRepository.findAll();
			apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(itemList);
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void deleteItemById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if ((sessionUser.getRole() == 2 && sessionUser != null)||(sessionUser.getRole() == 1 && sessionUser != null)) {
			Optional<Inventory> item = inventoryRepository.findById(id);
			if (item.isPresent()) {
				inventoryRepository.deleteById(id);

				apiResponseDtoBuilder.withMessage(Constants.ITEM_DELETE_SUCCESS).withStatus(HttpStatus.OK);
			} else {
				apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void updateItem(@Valid Inventory inventory, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if ((sessionUser.getRole() == 2 && sessionUser != null)||(sessionUser.getRole() == 1 && sessionUser != null)) {
			Optional<Inventory> inventoryChaeck = inventoryRepository.findById(inventory.getId());
			if (inventoryChaeck.isPresent()) {
				inventory.setUpdatedAt(new Date());
				inventoryRepository.save(inventory);
				apiResponseDtoBuilder.withMessage("Inventory Update Successfully.").withStatus(HttpStatus.OK)
						.withData(inventory);
			} else {
				apiResponseDtoBuilder.withMessage(Constants.NO_ITEM_EXISTS).withStatus(HttpStatus.OK);
				return;
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public void getInventoryById(ApiResponseDtoBuilder apiResponseDtoBuilder, long id) {
		User sessionUser = Utility.getSessionUser(userRepository);
		if ((sessionUser.getRole() == 2 && sessionUser != null)||(sessionUser.getRole() == 1 && sessionUser != null)) {
			Optional<Inventory> inventory = inventoryRepository.findById(id);
			if (inventory.isPresent()) {
				apiResponseDtoBuilder.withMessage("Inventory Details ..").withStatus(HttpStatus.OK).withData(inventory);
			} else {
				apiResponseDtoBuilder.withMessage(Constants.NO_ITEM_EXISTS).withStatus(HttpStatus.NOT_FOUND);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
		}
	}
}
