package com.playtika.qa.carsshop.dao.entity;


import lombok.*;
import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "user")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

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

