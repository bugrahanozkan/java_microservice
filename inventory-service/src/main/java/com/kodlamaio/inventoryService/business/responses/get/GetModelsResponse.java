package com.kodlamaio.inventoryService.business.responses.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetModelsResponse {
	
	private String id;
	
	private String name;
	
	private String brandId;
	
	private String brandName;
}
