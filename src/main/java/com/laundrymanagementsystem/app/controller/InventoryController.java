package com.laundrymanagementsystem.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.InventoryRequestDto;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.service.IInventoryService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class InventoryController {

	// addrItem

	@Autowired
	private IInventoryService iInventoryService;

	@PostMapping(value = "/Inventory/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addItem(@Valid @RequestBody InventoryRequestDto inventoryRequestDto,
			final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iInventoryService.addItem(inventoryRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}

	// getAllInventoryItems

	@GetMapping(value = "/Inventory/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAllItem() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iInventoryService.getAllItem(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/Inventory/getAvailable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getAvailableItem() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iInventoryService.getAvailableItem(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/Inventory/get/{id}")
	public ApiResponseDto getInventoryById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iInventoryService.getInventoryById(apiResponseDtoBuilder, id);
		return apiResponseDtoBuilder.build();
	}

	// deleteInventoryItem

	@DeleteMapping(value = "/Inventory/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto deleteItemById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iInventoryService.deleteItemById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	// updateInventoryItem

	@PutMapping(value = "/Inventory/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto updateItem(@Valid @RequestBody Inventory inventory) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		iInventoryService.updateItem(inventory, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

}
