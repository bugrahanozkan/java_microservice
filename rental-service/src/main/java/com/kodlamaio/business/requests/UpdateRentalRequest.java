package com.kodlamaio.business.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class UpdateRentalRequest {
	@NotBlank
	@NotNull
	private String id;
	
	@NotBlank
	@NotNull
	private String carId;
	
	@Min(value = 1)
	private  int rentForDays;
	
	@Min(value = 0)
	private double dailyPrice;
	
	@Min(value = 0)
	private double totalPrice;
}
