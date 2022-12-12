package com.kodlamaio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Headers;

@FeignClient(value = "rentalclient", url = "http://localhost:9010/rental/api/rentals/")
public interface RentalClient {
	@RequestMapping(method= RequestMethod.GET ,value = "totalpricebyid/{rentalId}")
	@Headers(value="Content-Type: application/json")
	double getTotalPrice(@PathVariable String rentalId);
}
