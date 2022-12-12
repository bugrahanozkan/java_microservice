package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.model.ModelDeletedEvent;
import com.kodlamaio.common.events.model.ModelUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.kafka.produce.InventoryProducer;
import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetModelsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateModelResponse;
import com.kodlamaio.inventoryService.dataAccess.ModelRepository;
import com.kodlamaio.inventoryService.entities.Model;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class ModelManager implements ModelService {
	private ModelRepository modelRepository;
	private ModelMapperService modelMapperService;
	private BrandService brandService;
	private InventoryProducer inventoryProducer;

	@Override
	public List<GetAllModelsResponse> getAll() {
		List<Model> models = modelRepository.findAll();
		List<GetAllModelsResponse> response = models.stream()
				.map(model -> this.modelMapperService.forResponse().map(model, GetAllModelsResponse.class))
				.collect(Collectors.toList());
		return response;
	}
	
	@Override
	public GetModelsResponse getById(String id) {
		checkIfModelExistById(id);
		Model model = modelRepository.findById(id).orElseThrow();
		return modelMapperService.forResponse().map(model, GetModelsResponse.class);
	}

	@Override
	public CreateModelResponse add(CreateModelRequest createModelRequest) {
		
		brandService.checkIfBrandExistById(createModelRequest.getBrandId());
		
		Model model = this.modelMapperService.forRequest().map(createModelRequest, Model.class);

		model.setId(UUID.randomUUID().toString());

		this.modelRepository.save(model);

		CreateModelResponse response = this.modelMapperService.forResponse().map(model, CreateModelResponse.class);
		return response;
	}

	@Override
	public UpdateModelResponse update(UpdateModelRequest updateModelRequest) {
		
		checkIfModelExistById(updateModelRequest.getId());
		
		brandService.checkIfBrandExistById(updateModelRequest.getBrandId());
		
		Model model = this.modelMapperService.forRequest().map(updateModelRequest, Model.class);

		model.setId(updateModelRequest.getId());

		Model savedModel=this.modelRepository.save(model);
		
		ModelUpdatedEvent event = modelMapperService.forResponse().map(savedModel, ModelUpdatedEvent.class);
		inventoryProducer.sendMessage(event);

		UpdateModelResponse response = this.modelMapperService.forResponse().map(model, UpdateModelResponse.class);
		return response;
	}

	@Override
	public void delete(String id) {
		ModelDeletedEvent event = new ModelDeletedEvent();
		event.setModelId(id);
		inventoryProducer.sendMessage(event);
		modelRepository.deleteById(id);
	}

	

	@Override
	public void checkIfModelExistById(String id) {
		if(modelRepository.getModelById(id) == null)
			throw new BusinessException(id+" Model Not Exist");
	}

}
