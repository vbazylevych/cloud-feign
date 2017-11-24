package com.playtika.qa.carsshop.dao.entity;

import com.playtika.qa.carsshop.domain.Car;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class CarEntity {
@Id @GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private String brand;
private int year;
private String color;

@OneToOne
private UserEntity user;




}
