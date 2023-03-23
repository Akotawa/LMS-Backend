package com.laundrymanagementsystem.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laundrymanagementsystem.app.constants.Constants;

@Entity
@Table(name = Constants.USER_TABLE_NAME)
@JsonIgnoreProperties
public class Customer extends User {

	private static final long serialVersionUID = 1L;
	private Boolean isVerified = Boolean.FALSE;
	private Long rewardPoints;
	private String homeAddress;

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Long getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Long rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

}
