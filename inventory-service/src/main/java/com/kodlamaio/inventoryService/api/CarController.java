package com.kodlamaio.inventoryService.api;

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

import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetCarsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
public class CarController {

	private CarService carService;
	
	@GetMapping("/getAll")
	List<GetAllCarsResponse> getAll(){
	return	carService.getAll();
	}
	
	@GetMapping("/getById/{id}")
	GetCarsResponse getById(@PathVariable  String id) {
		return carService.getById(id);
	}
	
	@PostMapping("/add")
	CreateCarResponse add(  @RequestBody  CreateCarRequest createCarRequest) {
		return carService.add(createCarRequest);
	}
	
	@PutMapping("/update")
	UpdateCarResponse update(@Valid  @RequestBody  UpdateCarRequest updateCarRequest) {
		return carService.update(updateCarRequest);
	}
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
		carService.delete(id);
	}
	@GetMapping("/checkIfCarAvailable/{id}")
	void checkIfCarAvailable(String id) {
		carService.checkIfCarAvailable(id);
	}
}
