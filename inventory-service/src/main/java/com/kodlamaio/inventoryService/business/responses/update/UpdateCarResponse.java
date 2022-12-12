package com.kodlamaio.inventoryService.business.responses.update;

import com.kodlamaio.inventoryService.entities.CarState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarResponse {
	 private String id;
	 
	 private double dailyPrice;

	 private int modelYear;

	 private String plate;

	 private CarState state;

	 private String modelId;
}
