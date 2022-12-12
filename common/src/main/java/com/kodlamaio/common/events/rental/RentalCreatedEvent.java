package com.kodlamaio.common.events.rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RentalCreatedEvent {

	private String message;

	private String carId;
}
