package com.laundrymanagementsystem.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laundrymanagementsystem.app.constants.Constants;

@Entity
@Table(name = Constants.USER_TABLE_NAME)
@JsonIgnoreProperties
@Transactional
public class Admin extends User {

	private static final long serialVersionUID = 1L;
	private Long laundryid;

	public Long getLaundryid() {
		return laundryid;
	}

	public void setLaundryid(Long laundryid) {
		this.laundryid = laundryid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
