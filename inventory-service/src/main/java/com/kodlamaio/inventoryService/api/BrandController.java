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
import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
public class BrandController {
	private BrandService brandService;
	
	
	@GetMapping("/getAll")
	List<GetAllBrandsResponse> getAll(){
		return brandService.getAll();
		
	}
	@GetMapping("/getById/{id}")
	GetBrandsResponse getById(@PathVariable String id) {
		return brandService.getById(id);
	}
	
	
	@PostMapping("/add")
	CreateBrandResponse add(@Valid @RequestBody  CreateBrandRequest createBrandRequest) {
		return brandService.add(createBrandRequest);
	}
	
	@PutMapping("/update")
	UpdateBrandResponse update(@Valid  @RequestBody  UpdateBrandRequest updateBrandRequest) {
		return brandService.update(updateBrandRequest);
	}
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
	brandService.delete(id);
	}
}
