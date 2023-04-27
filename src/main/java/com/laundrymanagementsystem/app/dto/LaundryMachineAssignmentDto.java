package com.laundrymanagementsystem.app.dto;

public class LaundryMachineAssignmentDto {
	private Long laundryMachineAssignmentId;

	public Long getLaundryMachineAssignmentId() {
		return laundryMachineAssignmentId;
	}

	public void setLaundryMachineAssignmentId(Long laundryMachineAssignmentId) {
		this.laundryMachineAssignmentId = laundryMachineAssignmentId;
	}

	private long orderID;

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}
}
