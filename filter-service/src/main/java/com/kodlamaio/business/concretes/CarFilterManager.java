package com.kodlamaio.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.business.abstracts.CarFilterService;
import com.kodlamaio.business.responses.GetAllFiltersResponse;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.dataAccess.CarFilterRepository;
import com.kodlamaio.entities.CarFilter;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CarFilterManager implements CarFilterService{
    private CarFilterRepository filterRepository;
    private ModelMapperService mapperService;
	@Override
	public List<GetAllFiltersResponse> getAll() {
		
		return filterRepository.findAll()
				.stream()
				.map(filter->mapperService.forResponse()
						.map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public List<GetAllFiltersResponse> getByBrandName(String brandName) {
		
		return filterRepository.findByBrandNameContainingIgnoreCase(brandName)
				.stream()
				.map(filter->mapperService.forResponse()
				.map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public List<GetAllFiltersResponse> getByModelName(String modelName) {
		
		return filterRepository.findByModelNameContainingIgnoreCase(modelName)
				.stream()
				.map(filter->mapperService.forResponse()
				.map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public List<GetAllFiltersResponse> getByPlate(String plate) {
		
		return filterRepository.findByPlateIgnoreCase(plate)
				.stream()
				.map(filter->mapperService.forResponse()
				.map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public List<GetAllFiltersResponse> searchByPlate(String plate) {
		
		return filterRepository.findByPlateContainingIgnoreCase(plate)
				.stream()
				.map(filter->mapperService.forResponse()
				.map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public List<GetAllFiltersResponse> searchByBrandName(String brandName) {
		return filterRepository.findByBrandNameContainingIgnoreCase(brandName)
				.stream()
				.map(filter-> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public List<GetAllFiltersResponse> searchByModelName(String modelName) {
		
		return filterRepository.findByModelNameContainingIgnoreCase(modelName)
				.stream()
				.map(filter->mapperService.forResponse()
				.map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public List<GetAllFiltersResponse> getByModelYear(int modelYear) {
		
		return filterRepository.findByModelYear(modelYear)
				.stream()
				.map(filter->mapperService.forResponse()
				.map(filter, GetAllFiltersResponse.class)).
				collect(Collectors.toList());
	}
	@Override
	public List<GetAllFiltersResponse> getByState(String state) {
		return filterRepository.findByState(state)
				.stream()
				.map(filter-> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public CarFilter getByCarId(String id) {
		
		return filterRepository.findByCarId(id);
	}
	@Override
	public List<CarFilter> getByModelId(String modelId) {
		
		return filterRepository.findByModelId(modelId);
	}
	@Override
	public List<CarFilter> getByBrandId(String brandId) {
		return filterRepository.findByBrandId(brandId);
	}
	@Override
	public void save(CarFilter filter) {
		filterRepository.save(filter);
		
	}
	@Override
	public void deleteById(String id) {
		filterRepository.deleteById(id);
	}
	@Override
	public void deleteAllByBrandId(String brandId) {
		filterRepository.deleteAllByBrandId(brandId);
		
	}
	@Override
	public void deleteAllByModelId(String modelId) {
		filterRepository.deleteAllByModelId(modelId);
		
	}
	
	

}
