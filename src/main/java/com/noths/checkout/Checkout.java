package com.noths.checkout;

import com.noths.checkout.domain.Product;
import com.noths.checkout.domain.PromotionalRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Checkout {

    private final List<PromotionalRule> promotionalRules;
    private Map<Product,Integer> scannedProductsMap = new HashMap<>();

    public Checkout(List<PromotionalRule> promotionalRules) {
        this.promotionalRules=promotionalRules;
    }

    public void scan(Product product) {
        Integer numberOfItems = scannedProductsMap.get(product);
        if(numberOfItems==null){
            numberOfItems=0;
        }
        scannedProductsMap.put(product,numberOfItems+1);
    }

    public Double total() {
        double total = 0;
        for(Product product: scannedProductsMap.keySet()){
            total+=product.getPrice()*scannedProductsMap.get(product);
        }
        return total;
    }
}
