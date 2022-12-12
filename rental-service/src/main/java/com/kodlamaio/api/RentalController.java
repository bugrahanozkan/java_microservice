package com.kodlamaio.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.business.abstracts.RentalService;
import com.kodlamaio.business.requests.CreateRentalRequest;
import com.kodlamaio.business.requests.UpdateRentalRequest;
import com.kodlamaio.business.responses.CreateRentalResponse;
import com.kodlamaio.business.responses.GetAllRentalsResponse;
import com.kodlamaio.business.responses.GetRentalResponse;
import com.kodlamaio.business.responses.UpdateRentalResponse;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor

public class RentalController {
	private RentalService rentalService;
	
	
	@GetMapping("getAll")
	List<GetAllRentalsResponse> getAll(){
		return rentalService.getAll();
	}
	
	@GetMapping("getById/{id}")
	GetRentalResponse getById(@PathVariable String id) {
		return rentalService.getById(id);
	}
	
	@PostMapping("add")
	CreateRentalResponse add(@Valid @RequestBody CreateRentalRequest request) {
		return rentalService.add(request);
	}
	
	@PutMapping("update")
	UpdateRentalResponse update(@Valid  @RequestBody UpdateRentalRequest request) {
		return rentalService.update(request);
	}
	
	@DeleteMapping("deleteById/{id}")
	void deleteById(@PathVariable String id) {
		rentalService.deleteById(id);
	}
}
