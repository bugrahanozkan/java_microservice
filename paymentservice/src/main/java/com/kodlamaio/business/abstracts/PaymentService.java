package com.kodlamaio.business.abstracts;

import java.util.List;

import com.kodlamaio.business.requests.CreatePaymentRequest;
import com.kodlamaio.business.responses.CreatePaymentResponse;
import com.kodlamaio.business.responses.GetAllPaymentsResponse;

public interface PaymentService {
	
	List<GetAllPaymentsResponse> getAll();
	

	CreatePaymentResponse add(CreatePaymentRequest createPaymentRequest);

     
	void checkBalanceEnough(double balance, double totalPrice);

}
