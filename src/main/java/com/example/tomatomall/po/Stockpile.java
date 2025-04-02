package com.example.tomatomall.po;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stockpiles")
public class Stockpile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "product_id", nullable = false)
    private String productId; // 关联商品表的主键

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "frozen", nullable = false)
    private Integer frozen;


}
