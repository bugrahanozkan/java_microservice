package com.kodlamaio.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllFiltersResponse {
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
