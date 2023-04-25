package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.InventoryRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.FeedbackRepository;
import com.laundrymanagementsystem.app.repository.InventoryRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class InventorySreviceImplTest {
	@InjectMocks
	InventorySreviceImpl inventorySreviceImpl;
	@Mock
	InventoryRepository inventoryRepository;
	@Mock
	FeedbackRepository feedbackRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addItem() {
		HttpServletRequest httpServletRequest = null;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();
		inventoryRequestDto.setEmail("test@gamil.com");
		inventoryRequestDto.setLaundryId(1L);
		inventoryRequestDto.setItemDescription("test");
		inventoryRequestDto.setItemName("test");
		inventoryRequestDto.setPayment("123");
		inventoryRequestDto.setQuantity(1l);
		inventoryRequestDto.setUserName("test");
		Inventory inventory = new Inventory();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		when(customMapper.inventoryRequestDtoToInventory(inventoryRequestDto)).thenReturn(inventory);
		inventorySreviceImpl.addItem(inventoryRequestDto, apiResponseDtoBuilder, httpServletRequest);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.ITEM_ADD_SUCCESS));

	}

	@Test
	public void getAllItem() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Inventory> itemList = new ArrayList<>();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		when(inventoryRepository.findAll()).thenReturn(itemList);
		inventorySreviceImpl.getAllItem(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));
	}

	@Test
	public void deleteItemById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Inventory inventory=new Inventory();
		inventory.setId(1l);
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Optional<Inventory> item =Optional.ofNullable(inventory);
		when(inventoryRepository.findById(id)).thenReturn(item);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		inventorySreviceImpl.deleteItemById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Item  Deleted Successfully"));
	}



	@Test
	public void updateItem() {
		
		Inventory inventory = new Inventory();
		inventory.setId(1l);
		Optional<Inventory> inventoryChaeck=Optional.ofNullable(inventory);
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Inventory> itemList = new ArrayList<>();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test123");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		when(inventoryRepository.findById(inventory.getId())).thenReturn(inventoryChaeck);
		inventorySreviceImpl.updateItem(inventory, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Inventory Update Successfully."));
	}

	public void getInventoryById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Inventory> itemList = new ArrayList<>();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		when(inventoryRepository.findAll()).thenReturn(itemList);
		inventorySreviceImpl.getInventoryById(apiResponseDtoBuilder, id);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.RATING_ADD_SUCCESS));
	}
}
