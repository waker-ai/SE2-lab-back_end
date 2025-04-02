package com.example.tomatomall.vo;


import com.example.tomatomall.po.Product;
import com.example.tomatomall.po.Specification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductVO {
    private String id; // 商品ID
    private String title; // 商品名称
    private BigDecimal price; // 商品价格
    private Double rate; // 商品评分
    private String description; // 商品描述
    private String cover; // 商品封面URL
    private String detail; // 商品详细说明
    private List<Specification>  specifications;


    public Product toPO() {
        Product product = new Product();
        product.setId(this.id);
        product.setTitle(this.title);
        product.setPrice(this.price);
        product.setRate(this.rate);
        product.setDescription(this.description);
        product.setCover(this.cover);
        product.setDetail(this.detail);
        product.setSpecifications(this.specifications);
        return product;
    }



}
