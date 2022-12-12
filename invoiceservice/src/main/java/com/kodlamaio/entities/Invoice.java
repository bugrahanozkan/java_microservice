package com.kodlamaio.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
	
	@Id
	private String id;
	
	private String rentalId;
	private double totalPrice;
	private String cardHolder;
}
