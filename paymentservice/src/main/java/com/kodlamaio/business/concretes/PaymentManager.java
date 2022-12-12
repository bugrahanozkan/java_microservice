package com.kodlamaio.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.kodlamaio.business.abstracts.PaymentService;
import com.kodlamaio.business.requests.CreatePaymentRequest;
import com.kodlamaio.business.responses.CreatePaymentResponse;
import com.kodlamaio.business.responses.GetAllPaymentsResponse;
import com.kodlamaio.client.RentalClient;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.dataAccess.PaymentRepository;
import com.kodlamaio.entities.Payment;
import com.kodlamaio.entities.PaymentStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService{

	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;
	
	@Override
	public CreatePaymentResponse add(CreatePaymentRequest createPaymentRequest) {

		
		Payment payment=modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setId(UUID.randomUUID().toString());
		payment.setPaymentStatus(PaymentStatus.ONAY);
		paymentRepository.save(payment);
		
		return modelMapperService.forResponse().map(payment, CreatePaymentResponse.class);
	}
	
	@Override
	public List<GetAllPaymentsResponse> getAll() {
		return paymentRepository.findAll()
				.stream()
				.map(payment->modelMapperService.forResponse()
				.map(payment, GetAllPaymentsResponse.class))
				.collect(Collectors.toList());
	}
	@Override
	public void checkBalanceEnough(double balance, double totalPrice) {
		if(balance<totalPrice) 	
			throw new BusinessException("Yetersiz Bakiye");
		
	}
}
