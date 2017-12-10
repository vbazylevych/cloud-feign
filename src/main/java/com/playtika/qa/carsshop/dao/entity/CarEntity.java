package com.playtika.qa.carsshop.dao.entity;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "car")
@Document
public class CarEntity {
    @Id
    @com.couchbase.client.java.repository.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "plate_number", unique = true, nullable = false, length = 25)
    @Field
    private String plateNumber;

    @Column(nullable = false, length = 50)
    @Field
    private String model;

    @Column(nullable = false)
    @Check(constraints = "year >= 1900")
    @Field
    private int year;

    @Column(length = 50)
    @Field
    private String color;

    public CarEntity(String plate_number, String model, int year, String color) {
        this.plateNumber = plate_number;
        this.model = model;
        this.year = year;
        this.color = color;
    }
}
