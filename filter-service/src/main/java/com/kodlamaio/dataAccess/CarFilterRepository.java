package com.kodlamaio.dataAccess;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kodlamaio.entities.CarFilter;

public interface CarFilterRepository extends MongoRepository<CarFilter, String>{
	
	 CarFilter findByCarId(String carId);
	 List<CarFilter> findByModelId(String modelId);
	 List<CarFilter> findByBrandId(String brandId);
	 
	 List<CarFilter> findByState(String state);
	 List<CarFilter> findByModelYear(int modelYear);
	 List<CarFilter> findByBrandNameIgnoreCase(String brandName);
	 List<CarFilter> findByModelNameIgnoreCase(String modelName);
	 List<CarFilter> findByPlateIgnoreCase(String plate);
	 List<CarFilter> findByPlateContainingIgnoreCase(String plate);
	 List<CarFilter> findByBrandNameContainingIgnoreCase(String brandName);
	 List<CarFilter> findByModelNameContainingIgnoreCase(String modelName);
	   
	 void deleteByCarId(String carId);
	 void deleteAllByBrandId(String brandId);
	 void deleteAllByModelId(String modelId);

}
