package com.example.tomatomall.po;

import com.example.tomatomall.vo.ProductVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id",nullable = false,updatable = false)
    private String id;//id

    @Column(name ="title",nullable = false,length = 50)
    private String title;//商品名

    @Column(name="price",nullable = false,precision = 10,scale=2)
    private BigDecimal price;//价格

    @Column(name="rate",nullable = false)
    private double rate;//评分

    @Column(name="description",length = 255)
    private String description;//商品描述

    @Column(name="cover",length = 500)
    private String cover;//商品封面url

    @Column(name="detail",length = 500)
    private String detail;//商品详细说明

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true) // 关联 Specification
    private List<Specification> specifications;

    public ProductVO toVO(){
        ProductVO productVO = new ProductVO();
        productVO.setId(this.id);
        productVO.setTitle(this.title);
        productVO.setPrice(this.price);
        productVO.setRate(this.rate);
        productVO.setDescription(this.description);
        productVO.setCover(this.cover);
        productVO.setDetail(this.detail);
        productVO.setSpecifications(this.specifications);
        return productVO;
    }


}
