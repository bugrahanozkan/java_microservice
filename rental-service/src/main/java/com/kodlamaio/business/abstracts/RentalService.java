package com.kodlamaio.business.abstracts;

import java.util.List;

import com.kodlamaio.business.requests.CreateRentalRequest;
import com.kodlamaio.business.requests.UpdateRentalRequest;
import com.kodlamaio.business.responses.CreateRentalResponse;
import com.kodlamaio.business.responses.GetAllRentalsResponse;
import com.kodlamaio.business.responses.GetRentalResponse;
import com.kodlamaio.business.responses.UpdateRentalResponse;



public interface RentalService {
	
	List<GetAllRentalsResponse> getAll();

	GetRentalResponse getById(String id);
	
	CreateRentalResponse add(CreateRentalRequest createRentalRequest);
	
	UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest);
	
	void deleteById(String id);
	
	
}
