package com.kodlamaio.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.kodlamaio.business.abstracts.CarFilterService;
import com.kodlamaio.common.events.InventoryCreatedEvent;
import com.kodlamaio.common.events.brand.BrandDeletedEvent;
import com.kodlamaio.common.events.brand.BrandUpdatedEvent;
import com.kodlamaio.common.events.car.CarDeletedEvent;
import com.kodlamaio.common.events.car.CarRentalDeletedEvent;
import com.kodlamaio.common.events.car.CarUpdatedEvent;
import com.kodlamaio.common.events.model.ModelDeletedEvent;
import com.kodlamaio.common.events.model.ModelUpdatedEvent;
import com.kodlamaio.common.events.rental.RentalCreatedEvent;
import com.kodlamaio.common.events.rental.RentalUpdatedCarEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.entities.CarFilter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryConsumer {
	private final CarFilterService filterService;
	private final ModelMapperService mapperService;
	 private static final Logger LOGGER = LoggerFactory.getLogger(InventoryConsumer.class);
	
	  @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "inventory-create"
	    )
	    public void consume(@Payload  InventoryCreatedEvent event) {
	        CarFilter filter = mapperService.forRequest().map(event, CarFilter.class);
	        filterService.save(filter);
	        LOGGER.info("Inventory created event consumed: {}", event);
	    }
	  
	  @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "car-delete"
	    )
	    public void consume(CarDeletedEvent event) {
	        filterService.deleteById(event.getCarId());
	        LOGGER.info("Car deleted event consumed: {}", event);
	    }
	  
	  @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "car-update"
	    )
	    public void consume(CarUpdatedEvent event) {
		  CarFilter filter = mapperService.forRequest().map(event, CarFilter.class);
	        String id = filterService.getByCarId(event.getCarId()).getId();
	        filter.setId(id);
	        filterService.save(filter);
	        LOGGER.info("Car updated event consumed: {}", event);
	    }
	  
	  @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "brand-delete"
	    )
	    public void consume(BrandDeletedEvent event) {
	        filterService.deleteAllByBrandId(event.getBrandId());

	        LOGGER.info("Brand deleted event consumed: {}", event);
	    }
	  
	  @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "brand-update"
	    )
	    public void consume(BrandUpdatedEvent event) {
	        filterService.getByBrandId(event.getId()).forEach(filter -> {
	            filter.setBrandName(event.getName());
	            filterService.save(filter);
	        });
	  }
	  
	  @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "model-delete"
	    )
	    public void consume(ModelDeletedEvent event) {
	        filterService.deleteAllByModelId(event.getModelId());
	        LOGGER.info("Model deleted event consumed: {}", event);
	    }
	  
	  @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "model-update"
	    )
	    public void consume(ModelUpdatedEvent event) {
	        filterService.getByModelId(event.getId()).forEach(filter -> {
	            filter.setModelName(event.getName());
	            filter.setBrandId(event.getBrandId());
	            filter.setBrandName(filterService.getByBrandId(event.getBrandId()).get(0).getBrandName());
	            filterService.save(filter);
	        });
	        LOGGER.info("Model updated event consumed: {}", event);
	    }
	  
	  @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "car-rental-create"
	    )
	    public void consume(RentalCreatedEvent event) {
		  CarFilter filter = filterService.getByCarId(event.getCarId());
	        filter.setState("NOT_AVAILABLE"); 
	        filterService.save(filter);
	        LOGGER.info("Car rental created event consumed: {}", event);
	    }
	  
	    @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "car-rental-delete"
	    )
	    public void consume(CarRentalDeletedEvent event) {
	    	CarFilter car = filterService.getByCarId(event.getCarId());
	        car.setState("AVAILABLE"); 
	        filterService.save(car);
	        LOGGER.info("Car rental deleted event consumed: {}", event);
	    }
	    
	    @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            , groupId = "car-rental-update"
	    )
	    public void consume(RentalUpdatedCarEvent event) {
	    	CarFilter oldCar = filterService.getByCarId(event.getOldCarId());
	    	CarFilter newCar = filterService.getByCarId(event.getNewCarId());
	        oldCar.setState("AVAILABLE");
	        newCar.setState("NOT_AVAILABLE"); 
	        filterService.save(oldCar);
	        filterService.save(newCar);
	        LOGGER.info("Car rental updated event consumed: {}", event);
	    }
}
