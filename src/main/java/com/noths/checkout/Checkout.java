package com.noths.checkout;

import com.noths.checkout.domain.Product;
import com.noths.checkout.domain.PromotionType;
import com.noths.checkout.domain.PromotionalRule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
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

    public Float total() {
        BigDecimal total = new BigDecimal("0");
        Map<String,PromotionalRule> productPromotionalRules = null;
        List<PromotionalRule> totalPromotionalRules = null;
        if(promotionalRules!=null){
            productPromotionalRules = promotionalRules.stream()
                    .filter(product -> product.getPromotionType()== PromotionType.PRODUCT)
                    .collect(Collectors.toMap(PromotionalRule::getProductCode,Function.identity()));

            totalPromotionalRules = promotionalRules.stream()
                    .filter(product -> product.getPromotionType()== PromotionType.TOTAL)
                    .collect(Collectors.toList());
            totalPromotionalRules.sort(Comparator.comparingDouble(PromotionalRule::getThreshold).reversed());
        }

        for(Product product: scannedProductsMap.keySet()){
            Integer numberOfItems = scannedProductsMap.get(product);
            Float price = product.getPrice();
            if(productPromotionalRules!=null){
                PromotionalRule rule = productPromotionalRules.get(product.getProductCode());
                if(rule!=null && numberOfItems>=rule.getThreshold()){
                    price=rule.getDiscount();
                }
            }
            total= total.add(new BigDecimal(price.toString()).multiply(new BigDecimal(numberOfItems.toString())));
        }
        if(totalPromotionalRules!=null) {
            for (PromotionalRule rule : totalPromotionalRules) {
                if (total.compareTo(new BigDecimal(rule.getThreshold().toString()))>0) {
                    BigDecimal discountedPrice = total.multiply(new BigDecimal(Float.toString(1 - rule.getDiscount())));
                    return discountedPrice.setScale(2, RoundingMode.CEILING).floatValue();
                }
            }
        }
        return total.floatValue();
    }
}
