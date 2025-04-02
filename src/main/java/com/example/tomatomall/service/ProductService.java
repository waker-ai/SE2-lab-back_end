package com.example.tomatomall.service;

import com.example.tomatomall.vo.ProductVO;

import java.util.List;

public interface ProductService {

    List<ProductVO> getAllProducts();
    ProductVO getProduct(String id);
    ProductVO updateProduct(ProductVO productVO);
    ProductVO createProduct(ProductVO productVO);
    boolean deleteProduct(String id);

}
