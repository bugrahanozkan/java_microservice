package com.kodlamaio.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.business.abstracts.InvoiceService;
import com.kodlamaio.business.requests.CreateInvoiceRequest;
import com.kodlamaio.business.responses.CreateInvoiceResponse;
import com.kodlamaio.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.dataAccess.InvoiceRepository;
import com.kodlamaio.entities.Invoice;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;

	@Override
	public List<GetAllInvoicesResponse> getAll() {

		return invoiceRepository.findAll()
				.stream()
				.map(invoice -> modelMapperService.forResponse()
				.map(invoice, GetAllInvoicesResponse.class))
				.collect(Collectors.toList());
	}

	@Override
	public CreateInvoiceResponse add(CreateInvoiceRequest request) {
		Invoice invoice = modelMapperService.forRequest().map(request, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());
		invoiceRepository.save(invoice);

		return modelMapperService.forResponse().map(invoice, CreateInvoiceResponse.class);
	}
}
