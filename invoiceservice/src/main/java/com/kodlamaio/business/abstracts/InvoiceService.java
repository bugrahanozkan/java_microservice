package com.kodlamaio.business.abstracts;

import java.util.List;

import com.kodlamaio.business.requests.CreateInvoiceRequest;
import com.kodlamaio.business.responses.CreateInvoiceResponse;
import com.kodlamaio.business.responses.GetAllInvoicesResponse;

public interface InvoiceService {
     List<GetAllInvoicesResponse> getAll();
	
	  CreateInvoiceResponse add(CreateInvoiceRequest request);
}
