package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.po.Product;
import com.example.tomatomall.repository.ProductRepository;
import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductVO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::toVO).collect(Collectors.toList());
    }

    @Override
    public ProductVO getProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null){
            return product.toVO();
        }
        return null;
    }

    @Override
    public ProductVO updateProduct(ProductVO productVO) {
        Product existproduct = productRepository.findById(productVO.getId()).orElse(null);
        if (existproduct != null) {
            // 更新产品信息
            existproduct.setTitle(productVO.getTitle());
            existproduct.setPrice(productVO.getPrice());
            existproduct.setRate(productVO.getRate());
            existproduct.setDescription(productVO.getDescription());
            existproduct.setCover(productVO.getCover());
            existproduct.setDetail(productVO.getDetail());
            existproduct.setSpecifications(productVO.getSpecifications());
            // 保存更新后的产品信息
            Product updatedProduct = productRepository.save(existproduct);
            return updatedProduct.toVO();
        }
        return null;
    }

    @Override
    public ProductVO createProduct(ProductVO productVO) {
        Product product = new Product();

        // 处理可能为空的字段，避免 NullPointerException
        product.setTitle(Optional.ofNullable(productVO.getTitle()).orElse("默认标题"));
        product.setPrice(Optional.ofNullable(productVO.getPrice()).orElse(BigDecimal.ZERO));  // 默认价格为 0
        product.setRate(Optional.ofNullable(productVO.getRate()).orElse(0.0)); // 默认评分 0.0
        product.setDescription(Optional.ofNullable(productVO.getDescription()).orElse("暂无描述"));
        product.setCover(Optional.ofNullable(productVO.getCover()).orElse("1")); // 默认封面为空字符串
        product.setDetail(Optional.ofNullable(productVO.getDetail()).orElse("暂无详情"));

        // 处理规格列表，允许为空
        if (productVO.getSpecifications() != null) {
            product.setSpecifications(productVO.getSpecifications());
        } else {
            product.setSpecifications(new ArrayList<>()); // 避免 null 引发异常
        }

        Product savedProduct = productRepository.save(product); // ✅ 接住返回值
        return savedProduct.toVO();
    }

    @Override
    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }





}
