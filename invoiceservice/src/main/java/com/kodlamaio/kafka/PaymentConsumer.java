package com.kodlamaio.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.business.abstracts.InvoiceService;
import com.kodlamaio.business.requests.CreateInvoiceRequest;
import com.kodlamaio.common.events.rental.RentalInvoiceCreatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentConsumer {
	private InvoiceService invoiceService;
	private  ModelMapperService mapperService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);
	
	@KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "RentalInvoiceCreate")
	public void consume(RentalInvoiceCreatedEvent event) {
		LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
		CreateInvoiceRequest request =mapperService.forRequest().map(event, CreateInvoiceRequest.class);
		invoiceService.add(request);
	}
}
