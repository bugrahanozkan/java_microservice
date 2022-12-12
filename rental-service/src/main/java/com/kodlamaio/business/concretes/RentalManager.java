package com.kodlamaio.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.business.abstracts.RentalService;
import com.kodlamaio.business.requests.CreateRentalRequest;
import com.kodlamaio.business.requests.UpdateRentalRequest;
import com.kodlamaio.business.responses.CreateRentalResponse;
import com.kodlamaio.business.responses.GetAllRentalsResponse;
import com.kodlamaio.business.responses.GetRentalResponse;
import com.kodlamaio.business.responses.UpdateRentalResponse;
import com.kodlamaio.client.CarClient;
import com.kodlamaio.client.PaymentServiceClient;
import com.kodlamaio.common.events.rental.RentalCreatedEvent;
import com.kodlamaio.common.events.rental.RentalInvoiceCreatedEvent;
import com.kodlamaio.common.events.rental.RentalPaymentCreatedEvent;
import com.kodlamaio.common.events.rental.RentalUpdatedCarEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.dataAccess.RentalRepository;
import com.kodlamaio.entities.Rental;
import com.kodlamaio.kafka.RentalCreateProducer;
import com.kodlamaio.kafka.RentalInvoiceCreateProducer;
import com.kodlamaio.kafka.RentalPaymentCreateProducer;
import com.kodlamaio.kafka.RentalUpdateProducer;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class RentalManager implements RentalService{

	private final RentalRepository rentalRepository;
	private final ModelMapperService mapperService;
	private final RentalCreateProducer  rentalCreateProducer;
	private final RentalUpdateProducer rentalUpdateProducer;
	private final CarClient carServiceClient;
	private final PaymentServiceClient paymentServiceClient;
	private final RentalPaymentCreateProducer rentalPaymentCreateProducer;
	private final RentalInvoiceCreateProducer rentalInvoiceCreateProducer;

	@Override
	public List<GetAllRentalsResponse> getAll() {
		List<GetAllRentalsResponse> response = rentalRepository.findAll()
				.stream()
				.map(r-> mapperService.forResponse().map(r, GetAllRentalsResponse.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public GetRentalResponse getById(String id) {
		checkIfRentalExists(id);
		Rental rental = rentalRepository.findById(id).get();
		return mapperService.forResponse().map(rental, GetRentalResponse.class);
	}

	@Override
	public CreateRentalResponse add(CreateRentalRequest request) {
		
		checkIfExistsRentalByCarId(request.getCarId());
		//carServiceClient.checkIfCarAvailable(request.getCarId());
		
		Rental rental = mapperService.forRequest().map(request, Rental.class);
		rental.setId(UUID.randomUUID().toString());
			
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
        rentalCreatedEvent.setCarId(rental.getCarId());
        rentalCreatedEvent.setMessage("Rental Created");
        
        double rentalTotalPrice =request.getDailyPrice() * request.getRentForDays();
        rental.setTotalPrice(rentalTotalPrice);		
        
        RentalPaymentCreatedEvent paymentCreatedEvent = new RentalPaymentCreatedEvent();
        paymentCreatedEvent.setRentalId(rental.getId());
        paymentCreatedEvent.setCardNo(request.getCardNo());
        paymentCreatedEvent.setCardHolder(request.getCardHolder());
        paymentCreatedEvent.setCardBalance(request.getCardBalance());
        paymentCreatedEvent.setTotalPrice(rentalTotalPrice);
        
        RentalInvoiceCreatedEvent invoiceCreatedEvent = new RentalInvoiceCreatedEvent();
        invoiceCreatedEvent.setRentalId(rental.getId());
        invoiceCreatedEvent.setCardHolder(request.getCardHolder());
        invoiceCreatedEvent.setTotalPrice(rentalTotalPrice);
 
        rentalPaymentCreateProducer.sendMessage(paymentCreatedEvent);
        paymentServiceClient.checkBalanceEnough(paymentCreatedEvent.getCardBalance(), paymentCreatedEvent.getTotalPrice());
        rentalRepository.save(rental);
        rentalCreateProducer.sendMessage(rentalCreatedEvent);
        rentalInvoiceCreateProducer.sendMessage(invoiceCreatedEvent);
		 
       
		return mapperService.forResponse().map(rental, CreateRentalResponse.class);
	}

	@Override
	public UpdateRentalResponse update(UpdateRentalRequest request) {
		checkIfRentalExists(request.getId());
		Rental rentalOld= rentalRepository.getRentalById(request.getId());
		Rental rental = mapperService.forRequest().map(request, Rental.class);
		rentalRepository.save(rental);	
		
		RentalUpdatedCarEvent rentalUpdatedCarEvent = new RentalUpdatedCarEvent();
		rentalUpdatedCarEvent.setOldCarId(rentalOld.getCarId());
		rentalUpdatedCarEvent.setNewCarId(rental.getCarId());
		
		rentalUpdateProducer.sendMessage(rentalUpdatedCarEvent);
		
		return mapperService.forResponse().map(rental, UpdateRentalResponse.class);
	}

	@Override
	public void deleteById(String id) {
		checkIfRentalExists(id);
		rentalRepository.deleteById(id);
	}
	
	private void checkIfRentalExists(String id) {
		if(rentalRepository.getRentalById(id) == null)
			throw new BusinessException(id+" Rental not exist");
	}
	
	private void checkIfExistsRentalByCarId(String carId) {
		if(rentalRepository.existsRentalByCarId(carId))
			throw new BusinessException(carId+" Car already rented");
	}

	

	
}
