package com.playtika.qa.carsshop.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ads")
    //    uniqueConstraints = {@UniqueConstraint(columnNames={"car_id", "deal_id"})})
public class AdsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BIGINT")
    private long id;

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false, columnDefinition = "BIGINT")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "car_id", nullable = false, columnDefinition = "BIGINT")
    private CarEntity car;

    @Column(nullable = false)
    @Check(constraints = "price > 0")
    private Integer price;

    @OneToMany(orphanRemoval=true, mappedBy = "ads")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "deal_id", columnDefinition = "BIGINT")
    private List<DealEntity> deal;

    public AdsEntity(UserEntity user, CarEntity car, Integer price, List<DealEntity> deal) {
        this.user = user;
        this.car = car;
        this.price = price;
        this.deal = deal;
    }
}

