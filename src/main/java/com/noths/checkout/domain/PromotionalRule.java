package com.noths.checkout.domain;

public class PromotionalRule {
    private final PromotionType promotionType;
    private final String productCode;
    private final Float discount;
    private final Float threshold;

    public PromotionalRule(String productCode, Float discount, Float threshold) {
        this.promotionType = PromotionType.PRODUCT;
        this.productCode = productCode;
        this.discount = discount;
        this.threshold = threshold;
    }

    public PromotionalRule(Float discount, Float threshold) {
        this.promotionType = PromotionType.TOTAL;
        this.productCode = null;
        this.discount = discount;
        this.threshold = threshold;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public String getProductCode() {
        return productCode;
    }

    public Float getDiscount() {
        return discount;
    }

    public Float getThreshold() {
        return threshold;
    }
}
