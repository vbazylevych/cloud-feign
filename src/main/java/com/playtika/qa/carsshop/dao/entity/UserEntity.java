package com.playtika.qa.carsshop.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    @OneToMany(mappedBy = "user")
    Set<CarEntity> carEntitySet;
}

