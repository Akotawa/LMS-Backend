package com.laundrymanagementsystem.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laundrymanagementsystem.app.constants.Constants;

@Entity
@Table(name = Constants.USER_TABLE_NAME)
@JsonIgnoreProperties
public class SuperAdmin extends User {

	private static final long serialVersionUID = 1L;
	

}
