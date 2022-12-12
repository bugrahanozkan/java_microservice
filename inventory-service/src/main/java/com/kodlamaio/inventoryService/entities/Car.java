package com.kodlamaio.inventoryService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cars")
public class Car {

	@Id
	@Column(name="id")
private String id;

	@Column(name="daily_price")
private double dailyPrice;

	@Column(name="model_year")
private int modelYear;

	@Column(name="plate")
private String plate;

	@Enumerated(EnumType.STRING)
private CarState state;

@ManyToOne()//bir modelde birden fazla araba(car) olabilir.
@JoinColumn(name="model_id")
private Model model;
}
