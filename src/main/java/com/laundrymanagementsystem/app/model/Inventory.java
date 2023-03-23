package com.laundrymanagementsystem.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laundrymanagementsystem.app.constants.Constants;

@Entity
@Table(name = Constants.INVENTORY_TABLE_NAME)
@JsonIgnoreProperties
public class Inventory {

	public static final long serialVersionUID = 1L;
	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String itemName;

	private String itemDescription;

	private Long quantity;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
