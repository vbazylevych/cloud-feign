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
@Table(name = "deal")
@Document
public class DealEntity {
    @Id
    @com.couchbase.client.java.repository.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne(targetEntity = AdsEntity.class)
    @JoinColumn(name = "ads_id", columnDefinition = "BIGINT")
    @Field
    private AdsEntity ads;

    public static enum Status {
        ACTIVATED, DECLINED, ACCEPTED
    }
    @Column(columnDefinition = "ENUM('ACTIVATED', 'DECLINED', 'ACCEPTED')", nullable = false)
   @Field
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Field
    private UserEntity user;

    @Column(nullable = false)
    @Check(constraints = "price > 0")
    @Field
    private int price;
}



