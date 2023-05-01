package com.laundrymanagementsystem.app.dto;

import java.util.Date;
import java.util.List;

public class OrderResponseDto {

	private Long id;
	private Long customerId;
	private Long laundryMachineId;
	private List<OrderDetailsResponseDto> details;
	private Date receivedDate;
	private Date completionDate;
	private Double invoicedAmount;
	private Integer orderStatus;
	private Long laundryId;
	private Boolean paymentStatus;
	private String email;
	private String mobileNumber;
	private String fullName;
	private String machineName;

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<OrderDetailsResponseDto> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetailsResponseDto> details) {
		this.details = details;
	}

	public Long getLaundryMachineId() {
		return laundryMachineId;
	}

	public void setLaundryMachineId(Long laundryMachineId) {
		this.laundryMachineId = laundryMachineId;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Double getInvoicedAmount() {
		return invoicedAmount;
	}

	public void setInvoicedAmount(Double invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getLaundryId() {
		return laundryId;
	}

	public void setLaundryId(Long laundryId) {
		this.laundryId = laundryId;
	}

	public Boolean getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
