package com.laundrymanagementsystem.app.dto;

public class ServiceResponseDto {
	private long serviceId;
	private String serviceName;
	private long laundryId;
	private String laundryName;
	private long priceId;
	private double price;

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public long getLaundryId() {
		return laundryId;
	}

	public void setLaundryId(long laundryId) {
		this.laundryId = laundryId;
	}

	public String getLaundryName() {
		return laundryName;
	}

	public void setLaundryName(String laundryName) {
		this.laundryName = laundryName;
	}

	public long getPriceId() {
		return priceId;
	}

	public void setPriceId(long priceId) {
		this.priceId = priceId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
