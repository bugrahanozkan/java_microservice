package com.kodlamaio.inventoryService.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="models")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {

	@Id
	@Column(name="id")
private String id;

	@Column(name="name")
private String name;

@ManyToOne //bir markanın(brand) birden fazla modeli(model) olabilir.
@JoinColumn(name="brand_id")
private Brand brand;

@OneToMany(mappedBy="model")//bir modelin birden fazla arabası olabilir
private List<Car> cars;
}
