package com.kodlamaio.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	private String rentalId;
	private double totalPrice;
	private String cardHolder;
}
