package com.kodlamaio.inventoryService.business.responses.create;

import com.kodlamaio.inventoryService.entities.CarState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarResponse {
 private String id;
 
private double dailyPrice;

private int modelYear;

private String plate;

private CarState state;

private String modelId;
}
