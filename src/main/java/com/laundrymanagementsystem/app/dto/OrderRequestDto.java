package com.laundrymanagementsystem.app.dto;

import java.util.List;

public class OrderRequestDto {

	private Long customerId;
	private List<ServiceWIthQuantity> serviceIdWithQuantity;
	private Long laundryId;

	public List<ServiceWIthQuantity> getServiceIdWithQuantity() {
		return serviceIdWithQuantity;
	}

	public void setServiceIdWithQuantity(List<ServiceWIthQuantity> serviceIdWithQuantity) {
		this.serviceIdWithQuantity = serviceIdWithQuantity;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getLaundryId() {
		return laundryId;
	}

	public void setLaundryId(Long laundryId) {
		this.laundryId = laundryId;
	}

}
