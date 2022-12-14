package com.kodlamaio.inventoryService.business.requests.create;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kodlamaio.inventoryService.entities.CarState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {
	@NotBlank
	@NotNull
private String plate;


private CarState state;

@Min(0)
private double dailyPrice;

@Min(2015)
private int modelYear;

@NotBlank
@NotNull
private String modelId;
}
