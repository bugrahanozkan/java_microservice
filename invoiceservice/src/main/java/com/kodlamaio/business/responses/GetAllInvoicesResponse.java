package com.kodlamaio.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllInvoicesResponse {
	private String id;
	private String rentalId;
	private double totalPrice;
	private String cardHolder;
}
