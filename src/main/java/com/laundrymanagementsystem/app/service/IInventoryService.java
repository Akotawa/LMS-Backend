package com.laundrymanagementsystem.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.InventoryRequestDto;
import com.laundrymanagementsystem.app.model.Inventory;

@Service
public interface IInventoryService {

	void addItem(@Valid InventoryRequestDto inventoryRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	void getAllItem(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deleteItemById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateItem(@Valid Inventory inventory, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getInventoryById(ApiResponseDtoBuilder apiResponseDtoBuilder, long id);

}
