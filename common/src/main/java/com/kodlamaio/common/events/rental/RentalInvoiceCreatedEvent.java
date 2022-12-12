package com.kodlamaio.common.events.rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalInvoiceCreatedEvent {
	private String rentalId;
	private double totalPrice;
	private String cardNo;
	private String cardHolder;
	private double cardBalance;
}