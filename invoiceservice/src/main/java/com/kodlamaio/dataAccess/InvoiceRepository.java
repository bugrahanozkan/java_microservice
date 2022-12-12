package com.kodlamaio.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, String>{

}
