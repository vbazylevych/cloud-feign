package com.playtika.qa.carsshop.dao.entity;

import com.playtika.qa.carsshop.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(name = "car")
public class CarEntity {
@Id @GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@Column(unique = true, nullable = false, length = 25)
private String plate_number;

@Column(nullable = false, length = 50)
private String model;

@Column(nullable = false)
@Check(constraints = "year >= 1900")
private int year;

@Column(length = 50)
private String color;
}
