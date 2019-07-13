package com.noths.checkout;

import com.noths.checkout.domain.Product;
import com.noths.checkout.domain.PromotionType;
import com.noths.checkout.domain.PromotionalRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Map<String,PromotionalRule> productPromotionalRules = null;
        if(promotionalRules!=null){
            productPromotionalRules = promotionalRules.stream()
                    .filter(product -> product.getPromotionType()== PromotionType.PRODUCT)
                    .collect(Collectors.toMap(PromotionalRule::getProductCode,Function.identity()));
        }

        for(Product product: scannedProductsMap.keySet()){
            Integer numberOfItems = scannedProductsMap.get(product);
            double price = product.getPrice();
            if(productPromotionalRules!=null){
                PromotionalRule rule = productPromotionalRules.get(product.getProductCode());
                if(rule!=null && numberOfItems>=rule.getThreshold()){
                    price=rule.getDiscount();
                }
            }
            total+= price * numberOfItems;
        }
        return total;
    }
}
