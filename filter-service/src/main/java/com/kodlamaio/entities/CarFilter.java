package com.kodlamaio.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collation = "filter-inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarFilter {
	@Id
	private String id;
	
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
