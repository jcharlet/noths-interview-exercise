package com.noths.checkout;

import com.noths.checkout.domain.Product;
import com.noths.checkout.domain.PromotionType;
import com.noths.checkout.domain.PromotionalRule;
import com.noths.checkout.domain.ScannedProduct;
import com.noths.checkout.repository.IScannedProductsRepository;
import com.noths.checkout.repository.impl.ScannedProductMemoryRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Checkout {

    private Map<String, PromotionalRule> productPromotionalRules = new HashMap<>();
    private List<PromotionalRule> totalPromotionalRules = new ArrayList<>();

    IScannedProductsRepository scannedProductsRepository = new ScannedProductMemoryRepository();

    public Checkout(List<PromotionalRule> promotionalRules) {
        if(promotionalRules!=null) {
            productPromotionalRules = promotionalRules.stream()
                    .filter(product -> product.getPromotionType()== PromotionType.PRODUCT)
                    .collect(Collectors.toMap(PromotionalRule::getProductCode,Function.identity()));

            totalPromotionalRules = promotionalRules.stream()
                    .filter(product -> product.getPromotionType()== PromotionType.TOTAL)
                    .collect(Collectors.toList());
            totalPromotionalRules.sort(Comparator.comparingDouble(PromotionalRule::getThreshold).reversed());
        }
    }

    public void scan(Product product) {
        ScannedProduct scannedProduct = scannedProductsRepository.findByProductCode(product.getProductCode());
        if(scannedProduct==null){
            scannedProductsRepository.save(new ScannedProduct(product.getProductCode(),product.getName(),product.getPrice(),1));
        }else{
            Integer nbItems = scannedProduct.getNbItems();
            scannedProductsRepository.save(new ScannedProduct(product.getProductCode(),product.getName(),product.getPrice(),nbItems+1));
        }
    }

    public Float total() {
        BigDecimal total = new BigDecimal("0");

        for(ScannedProduct product: scannedProductsRepository.findAll()){
            Integer nbItems = product.getNbItems();
            Float discountedPrice = getDiscountedPriceForProduct(product, nbItems);
            Float price = discountedPrice!=null?discountedPrice:product.getPrice();

            total= total.add(new BigDecimal(price.toString()).multiply(new BigDecimal(nbItems.toString())));
        }

        Float discountedPrice = getDiscountedPriceForTotal(total);
        if (discountedPrice != null) return discountedPrice;

        return total.floatValue();
    }

    private Float getDiscountedPriceForTotal(BigDecimal total) {
        for (PromotionalRule rule : totalPromotionalRules) {
            if (total.compareTo(new BigDecimal(rule.getThreshold().toString()))>0) {
                BigDecimal discountedPrice = total.multiply(new BigDecimal(Float.toString(1 - rule.getDiscount())));
                return discountedPrice.setScale(2, RoundingMode.CEILING).floatValue();
            }
        }
        return null;
    }

    private Float getDiscountedPriceForProduct(Product product, Integer nbItems) {
        if(!productPromotionalRules.isEmpty()){
            PromotionalRule rule = productPromotionalRules.get(product.getProductCode());
            if(rule!=null && nbItems>=rule.getThreshold()){
                return rule.getDiscount();
            }
        }
        return null;
    }
}
