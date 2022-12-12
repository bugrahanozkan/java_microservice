package com.kodlamaio.inventoryService.business.requests.update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kodlamaio.inventoryService.entities.CarState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	@NotBlank
	@NotNull
 private String id;
	 
	@Min(value = 0)
	 private double dailyPrice;

	@Min(value=2015)
	 private int modelYear;

	@NotBlank
	@NotNull
	 private String plate;

	@NotNull
	 private CarState state;

	@NotBlank
	@NotNull
	 private String modelId;
}
