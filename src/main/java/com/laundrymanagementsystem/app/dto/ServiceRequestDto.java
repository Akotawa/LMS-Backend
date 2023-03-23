package com.laundrymanagementsystem.app.dto;

public class ServiceRequestDto {

	private String serviceName;

	private String serviceDescription;
	
	private String quantity;
	
	private long laundryId;

	public long getLaundryId() {
		return laundryId;
	}

	public void setLaundryId(long laundryId) {
		this.laundryId = laundryId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
