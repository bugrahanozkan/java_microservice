package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.brand.BrandUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.kafka.produce.InventoryProducer;
import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;
import com.kodlamaio.inventoryService.dataAccess.BrandRepository;
import com.kodlamaio.inventoryService.entities.Brand;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {

	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;
    private InventoryProducer inventoryProducer;
	@Override
	public List<GetAllBrandsResponse> getAll() {

		List<Brand> brands = brandRepository.findAll();
		List<GetAllBrandsResponse> response = brands.stream()
				.map(brand -> this.modelMapperService.forResponse().map(brand, GetAllBrandsResponse.class))
				.collect(Collectors.toList());
		return response;
	}
	@Override
	public GetBrandsResponse getById(String id) {
		checkIfBrandExistById(id);
		Brand brand= brandRepository.getBrandById(id);
		GetBrandsResponse response = this.modelMapperService.forResponse().map(brand, GetBrandsResponse.class);
				
		return response;
	}

	@Override
	public CreateBrandResponse add(CreateBrandRequest createBrandRequest) {
		
		checkIfBrandExistsByName(createBrandRequest.getName());
		
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		brand.setId(UUID.randomUUID().toString());
		
		this.brandRepository.save(brand);
		
		CreateBrandResponse createBrandResponse = this.modelMapperService.forResponse()
				.map(brand, CreateBrandResponse.class);
		return createBrandResponse;
	}
	
	@Override
	public UpdateBrandResponse update(UpdateBrandRequest updateBrandRequest) {
		
		checkIfBrandExistById(updateBrandRequest.getId());
		checkIfBrandExistsByName(updateBrandRequest.getName());
		Brand brand= this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		
		brand.setId(updateBrandRequest.getId());
		
		Brand savedBrand =this.brandRepository.save(brand);
		BrandUpdatedEvent event = modelMapperService.forResponse().map(savedBrand, BrandUpdatedEvent.class);
		inventoryProducer.sendMessage(event);
		UpdateBrandResponse response= this.modelMapperService.forResponse().map(brand, UpdateBrandResponse.class);
		return response;
	}

	@Override
	public void delete(String id) {
		checkIfBrandExistById(id);
         brandRepository.deleteById(id);		
	}
	
	
	private void checkIfBrandExistsByName(String name) {
		Brand brand = this.brandRepository.findByName(name);
		if(brand!=null) {
		    throw new BusinessException("BRAND.EXISTS");
		}
	}

	@Override
	public void checkIfBrandExistById(String id) {
		if(brandRepository.getBrandById(id) == null)
			throw new BusinessException(id+ " Id Not Exist");
	}
	
	
}

