package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetCarsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;

public interface CarService {

	List<GetAllCarsResponse> getAll();
	
	CreateCarResponse add(CreateCarRequest createCarRequest);
	
	UpdateCarResponse update(UpdateCarRequest updateCarRequest);
	
	void delete(String id);
	
    void changeCarState(String id);
	
	void changeCarState(String oldCarId, String newCarId);
	
	GetCarsResponse getById(String id);
	
	void checkIfCarAvailable(String id);
}
