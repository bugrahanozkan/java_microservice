package com.kodlamaio.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.entities.Rental;

public interface RentalRepository extends JpaRepository<Rental, String>{
	boolean existsRentalByCarId(String id);
	Rental getRentalById(String id);
}
