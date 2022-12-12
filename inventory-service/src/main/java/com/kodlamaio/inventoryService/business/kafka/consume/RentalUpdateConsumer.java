package com.kodlamaio.inventoryService.business.kafka.consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rental.RentalUpdatedCarEvent;
import com.kodlamaio.inventoryService.business.abstracts.CarService;

import lombok.AllArgsConstructor;



public class RentalUpdateConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalCreateConsumer.class);
	   
	   CarService carService;

	    @KafkaListener(
	            topics = "${spring.kafka.topic.name}"
	            ,groupId = "rental-update"
	    )
	    
	    public void consume(RentalUpdatedCarEvent event) {
	    	LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
	    	
	    	carService.changeCarState(event.getOldCarId(), event.getNewCarId());
	    	LOGGER.info(event.getOldCarId()+" changed to "+ event.getNewCarId());
	    }
}
