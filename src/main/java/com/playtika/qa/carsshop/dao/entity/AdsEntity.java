package com.playtika.qa.carsshop.dao.entity;

import lombok.*;
import org.hibernate.annotations.Check;


import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;


import javax.persistence.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ads")
@Document
public class AdsEntity {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn (name = "user_id", nullable = false, columnDefinition = "BIGINT")
    @Field
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_id", nullable = false, columnDefinition = "BIGINT")
    @Field
    private CarEntity car;

    @Column(nullable = false)
    @Check(constraints = "price > 0")
    @Field
    private Integer price;

    @OneToMany(mappedBy = "ads", fetch = FetchType.LAZY)
    @Column(name = "deal_id", columnDefinition = "BIGINT")
    @Field
    private Set<DealEntity> deal;

    public AdsEntity(UserEntity user, CarEntity car, Integer price, Set<DealEntity> deal) {
        this.user = user;
        this.car = car;
        this.price = price;
        this.deal = deal;
    }
}

