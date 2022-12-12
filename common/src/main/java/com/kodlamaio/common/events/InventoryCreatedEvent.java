package com.kodlamaio.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCreatedEvent {
	private String carId;
	private String plate;
	private int modelYear;
	private double dailyPrice;
	private String state;	
	
	private String modelId;
	private String modelName;
	
	private String brandId;
	private String brandName;
}
