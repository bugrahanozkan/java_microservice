package com.kodlamaio.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="payments")
public class Payment {
	
	@Id
	@Column(name="id")
	private String id;
 
	@Column(name="card_holder")
	private String cardHolder;
	
	@Column(name="card_no")
	private String cardNo;
	
	@Column(name="rentalId")
	private String rentalId;
 
	@Column(name="balance")
	private double balance;
 
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

}
