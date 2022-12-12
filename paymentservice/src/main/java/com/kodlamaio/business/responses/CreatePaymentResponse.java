package com.kodlamaio.business.responses;

import java.time.LocalDate;

import com.kodlamaio.entities.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreatePaymentResponse {
	private String id;
	private String rentalId;
	private double doublePrice;
	private String cardNo;
	private String cardHolder;
	private double cardBalance;
	private PaymentStatus paymentStatus;
}
