package com.playtika.qa.carsshop.dao.entity;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.*;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "user")
@Document
public class UserEntity {

    @Id
    @com.couchbase.client.java.repository.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(nullable = false, length = 20)
    @Field
    private String name;

    @Column(length = 50)
    @Field
    private String surname;

    @Column(nullable = false)
    @Field
    private String contact;


    public UserEntity(String name, String surname, String contact) {
        this.name = name;
        this.surname = surname;
        this.contact = contact;
    }
}

