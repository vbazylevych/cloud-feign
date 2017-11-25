package com.playtika.qa.carsshop.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "ads")
    //    uniqueConstraints = {@UniqueConstraint(columnNames={"car_id", "deal_id"})})
public class AdsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity car;

    @Column(nullable = false)
    @Check(constraints = "price > 0")
    private Integer price;

    @OneToMany(mappedBy = "ads")
    @Column(name = "deal_id")
    private List<DealEntity> deal;
}

