package com.laundrymanagementsystem.app.mapper;

import javax.validation.Valid;

import org.mapstruct.Mapper;

import com.laundrymanagementsystem.app.dto.AdminRequestDto;
import com.laundrymanagementsystem.app.dto.CustomerRequestDto;
import com.laundrymanagementsystem.app.dto.EmployeeRequestDto;
import com.laundrymanagementsystem.app.dto.FeedbackRequestDto;
import com.laundrymanagementsystem.app.dto.InventoryRequestDto;
import com.laundrymanagementsystem.app.dto.LaundryRequestDto;
import com.laundrymanagementsystem.app.dto.OrderRequestDto;
import com.laundrymanagementsystem.app.dto.PromoCodeRequestDto;
import com.laundrymanagementsystem.app.dto.RatingRequestDto;
import com.laundrymanagementsystem.app.dto.ServiceRequestDto;
import com.laundrymanagementsystem.app.dto.UserRequestDto;
import com.laundrymanagementsystem.app.model.Admin;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.Feedback;
import com.laundrymanagementsystem.app.model.Inventory;
import com.laundrymanagementsystem.app.model.Laundry;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.PromoCode;
import com.laundrymanagementsystem.app.model.Rating;
import com.laundrymanagementsystem.app.model.Services;
import com.laundrymanagementsystem.app.model.SuperAdmin;
import com.laundrymanagementsystem.app.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);

	SuperAdmin adminRequestDtoToSuperAdmin(AdminRequestDto adminRequestDto);
	Admin adminRequestDtoToAdmin(AdminRequestDto adminRequestDto);
	Customer userRequestDtoToCustomer(UserRequestDto userRequestDto);

	Employee userRequestDtoToEmployee(@Valid EmployeeRequestDto employeeRequestDto);
	PromoCode promoCodeRequestDtoToPromoCode(@Valid PromoCodeRequestDto promoCodeRequestDto);

	Rating ratingRequestDtoToRating(@Valid RatingRequestDto ratingRequestDto);
	Feedback feedbackRequestDtoToFeedback(@Valid FeedbackRequestDto feedbackRequestDto);

	Laundry laundryRequestDtoTolandry(@Valid LaundryRequestDto laundryRequestDto);

	Customer customerRequestDtoToCustomer(@Valid CustomerRequestDto customerRequestDto);

	Order  orderRequestDtoToOrder(@Valid OrderRequestDto orderRequestDto);
	
	Inventory  inventoryRequestDtoToInventory(@Valid InventoryRequestDto inventoryRequestDto);

	Services  serviceRequestDtoToServices(@Valid ServiceRequestDto serviceRequestDto);
}