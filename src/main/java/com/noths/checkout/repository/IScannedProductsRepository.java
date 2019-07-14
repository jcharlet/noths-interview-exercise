package com.noths.checkout.repository;

import com.noths.checkout.domain.ScannedProduct;

import java.util.List;

public interface IScannedProductsRepository {
    ScannedProduct findByProductCode(String productCode);

    void save(ScannedProduct scannedProduct);

    List<ScannedProduct> findAll();
}
