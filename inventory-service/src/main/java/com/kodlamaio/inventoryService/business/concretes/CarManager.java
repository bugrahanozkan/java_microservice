package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.InventoryCreatedEvent;
import com.kodlamaio.common.events.car.CarDeletedEvent;
import com.kodlamaio.common.events.car.CarUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.kafka.produce.InventoryProducer;
import com.kodlamaio.inventoryService.business.kafka.produce.InvetoryCreateProducer;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetCarsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;
import com.kodlamaio.inventoryService.entities.CarState;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class CarManager implements CarService {
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private ModelService  modelService;
	private InventoryProducer inventoryProducer;
	private InvetoryCreateProducer invetoryCreateProducer;


	@Override
	public List<GetAllCarsResponse> getAll() {
		List<GetAllCarsResponse> response = carRepository.findAll()
				.stream()
				.map(car-> modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.collect(Collectors.toList());
		return response;
	}
	
	@Override
	public GetCarsResponse getById(String id) {
		checkIfCarExistById(id);
		Car car = carRepository.findById(id).orElseThrow();
		return modelMapperService.forResponse().map(car, GetCarsResponse.class);
	}

	@Override
	public CreateCarResponse add(CreateCarRequest request) {
		
		checkIfCarExistByPlate(request.getPlate());
		modelService.checkIfModelExistById(request.getModelId());
		
		Car car = modelMapperService.forRequest().map(request, Car.class);
		car.setId(UUID.randomUUID().toString());
		carRepository.save(car);
		
		Car savedCar = carRepository.findById(car.getId()).orElseThrow();
		InventoryCreatedEvent event = new InventoryCreatedEvent();
		event.setBrandId(savedCar.getModel().getBrand().getId());
		event.setBrandName(savedCar.getModel().getBrand().getName());
		event.setCarId(savedCar.getId());
		event.setDailyPrice(savedCar.getDailyPrice());
		event.setModelId(savedCar.getModel().getId());
		event.setModelName(savedCar.getModel().getName());
		event.setModelYear(savedCar.getModelYear());
		event.setPlate(savedCar.getPlate());
		//event.setState(savedCar.getState().name());
		event.setState(savedCar.getState().name());
		invetoryCreateProducer.sendMessage(event);
		
		CreateCarResponse response = modelMapperService.forResponse().map(car, CreateCarResponse.class);
		return response;
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest request) {
		checkIfCarExistById(request.getId());
		checkIfCarExistByPlate(request.getPlate());
		modelService.checkIfModelExistById(request.getModelId());
		Car car = modelMapperService.forRequest().map(request, Car.class);
		Car savedCar = carRepository.save(car);
		
		
		CarUpdatedEvent event = modelMapperService.forResponse().map(savedCar, CarUpdatedEvent.class);
		inventoryProducer.sendMessage(event);
		
		UpdateCarResponse response = modelMapperService.forResponse().map(car, UpdateCarResponse.class);
		return response;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistById(id);
		
		CarDeletedEvent event = new CarDeletedEvent();
		event.setCarId(id);
		inventoryProducer.sendMessage(event);
		
		carRepository.deleteById(id);	
	}
	
	@Override
	public void changeCarState(String id) {
		checkIfCarExistById(id);
		Car car = carRepository.getCarById(id);
		car.setState(CarState.NOT_AVAILABLE);
		carRepository.save(car);
	}
	
	@Override
	public void changeCarState(String oldCarId, String newCarId) {
		checkIfCarExistById(oldCarId);
		checkIfCarExistById(newCarId);
		Car oldCar = carRepository.getCarById(oldCarId);
		Car newCar = carRepository.getCarById(newCarId);
		oldCar.setState(CarState.AVAILABLE);
		newCar.setState(CarState.NOT_AVAILABLE);
		carRepository.save(oldCar);
		carRepository.save(newCar);
	}
	
	@Override
	public void checkIfCarAvailable(String id) {
		checkIfCarExistById(id);
		Car car = carRepository.getCarById(id);
		if(car.getState().equals(CarState.NOT_AVAILABLE))
			throw new BusinessException("Car Not Available");
	}
	
	private void checkIfCarExistById(String id) {
		if(carRepository.getCarById(id) == null)
			throw new BusinessException(id+" Car Not Exist");
	}
	
	private void checkIfCarExistByPlate(String name) {
		if(carRepository.existsCarByPlate(name))
			throw new BusinessException("Car Exist");
	}
}
