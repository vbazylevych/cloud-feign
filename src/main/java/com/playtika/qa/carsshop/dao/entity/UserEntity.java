package com.playtika.qa.carsshop.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 50)
    private String surname;

    @Column(nullable = false)
    private String contact;


    public UserEntity(String name, String surname, String contact) {
        this.name = name;
        this.surname = surname;
        this.contact = contact;
    }
}

