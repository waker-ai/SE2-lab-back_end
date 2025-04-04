package com.example.tomatomall.service;

import com.example.tomatomall.po.Stockpile;

import java.util.Optional;

public interface StockpileService {
    Optional<Stockpile> adjustStockpile(String productId, Integer amount);

    Optional<Stockpile> getStockpileByProductId(String productId);
}