package com.noths.checkout.repository.impl;

import com.noths.checkout.domain.ScannedProduct;
import com.noths.checkout.repository.IScannedProductsRepository;

import java.util.ArrayList;
import java.util.List;

public class ScannedProductMemoryRepository implements IScannedProductsRepository {
    List<ScannedProduct> scannedProducts = new ArrayList<>();

    @Override
    public ScannedProduct findByProductCode(String productCode) {
        return scannedProducts.stream().filter(product -> productCode.equals(product.getProductCode())).findFirst().orElse(null);
    }

    @Override
    public void save(ScannedProduct scannedProduct) {
        for (int i = 0; i < scannedProducts.size(); i++) {
            if(scannedProducts.get(i).getProductCode()==scannedProduct.getProductCode()){
                scannedProduct.setNbItems(scannedProducts.get(i).getNbItems()+1);
                scannedProducts.remove(i);
                scannedProducts.add(i,scannedProduct);
                return;
            }
        }
        scannedProducts.add(scannedProduct);

    }

    @Override
    public List<ScannedProduct> findAll() {
        return scannedProducts;
    }
}
