package com.laundrymanagementsystem.app.dto;

public class DashboardRequestDto {

	private long totalOrder;
	private long totalCustomer;
	private long totalEmployee;
	private long totalLaundry;
	private long totalAdmin;
	private long totalPendingOrder;
	private long totalReceivedOrder;
	private long totalCompletedOrder;
	private long totalDeliveredOrder;
	private long totalCancelOrder;
	private long totalService;

	public long getTotalService() {
		return totalService;
	}

	public void setTotalService(long totalService) {
		this.totalService = totalService;
	}

	public long getTotalPendingOrder() {
		return totalPendingOrder;
	}

	public void setTotalPendingOrder(long totalPendingOrder) {
		this.totalPendingOrder = totalPendingOrder;
	}

	public long getTotalReceivedOrder() {
		return totalReceivedOrder;
	}

	public void setTotalReceivedOrder(long totalReceivedOrder) {
		this.totalReceivedOrder = totalReceivedOrder;
	}

	public long getTotalCompletedOrder() {
		return totalCompletedOrder;
	}

	public void setTotalCompletedOrder(long totalCompletedOrder) {
		this.totalCompletedOrder = totalCompletedOrder;
	}

	public long getTotalDeliveredOrder() {
		return totalDeliveredOrder;
	}

	public void setTotalDeliveredOrder(long totalDeliveredOrder) {
		this.totalDeliveredOrder = totalDeliveredOrder;
	}

	public long getTotalCancelOrder() {
		return totalCancelOrder;
	}

	public void setTotalCancelOrder(long totalCancelOrder) {
		this.totalCancelOrder = totalCancelOrder;
	}

	public long getTotalAdmin() {
		return totalAdmin;
	}

	public void setTotalAdmin(long totalAdmin) {
		this.totalAdmin = totalAdmin;
	}

	public long getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(long totalOrder) {
		this.totalOrder = totalOrder;
	}

	public long getTotalCustomer() {
		return totalCustomer;
	}

	public void setTotalCustomer(long totalCustomer) {
		this.totalCustomer = totalCustomer;
	}

	public long getTotalEmployee() {
		return totalEmployee;
	}

	public void setTotalEmployee(long totalEmployee) {
		this.totalEmployee = totalEmployee;
	}

	public long getTotalLaundry() {
		return totalLaundry;
	}

	public void setTotalLaundry(long totalLaundry) {
		this.totalLaundry = totalLaundry;
	}
}
