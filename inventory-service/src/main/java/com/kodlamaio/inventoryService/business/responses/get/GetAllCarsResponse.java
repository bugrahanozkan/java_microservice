package com.kodlamaio.inventoryService.business.responses.get;

import com.kodlamaio.inventoryService.entities.CarState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCarsResponse {
	private String id;
	private double dailyPrice;
	private int modelYear;
	private String plate;
	private String modelId;
	private String modelBrandId;
	private String modelBrandName;
	private String modelName;
	private CarState state;
}