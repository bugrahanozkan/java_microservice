package com.kodlamaio.business.abstracts;

import java.util.List;

import com.kodlamaio.business.responses.GetAllFiltersResponse;
import com.kodlamaio.entities.CarFilter;

public interface CarFilterService {
	List<GetAllFiltersResponse> getAll();
    List<GetAllFiltersResponse> getByBrandName(String brandName);
    List<GetAllFiltersResponse> getByModelName(String modelName);
    List<GetAllFiltersResponse> getByPlate(String plate);
    List<GetAllFiltersResponse> searchByPlate(String plate);
    List<GetAllFiltersResponse> searchByBrandName(String brandName);
    List<GetAllFiltersResponse> searchByModelName(String modelName);
    List<GetAllFiltersResponse> getByModelYear(int modelYear);
    List<GetAllFiltersResponse> getByState(String state);
    
    
    CarFilter getByCarId(String id);
    List<CarFilter> getByModelId(String modelId);
    List<CarFilter> getByBrandId(String brandId);
    void save(CarFilter filter);
    void deleteById(String id);
    void deleteAllByBrandId(String brandId);
    void deleteAllByModelId(String modelId);
}
