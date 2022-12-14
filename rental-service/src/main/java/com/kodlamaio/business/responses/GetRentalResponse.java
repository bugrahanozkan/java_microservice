package com.kodlamaio.business.responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetRentalResponse {
	private String id;
	private String carId;
	private LocalDateTime dateStarted;
	private  int rentForDays;
	private double dailyPrice;
	private double totalPrice;
}
