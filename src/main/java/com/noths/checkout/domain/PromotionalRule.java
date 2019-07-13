package com.noths.checkout.domain;

public class PromotionalRule {
    private final PromotionType promotionType;
    private final String productCode;
    private final float discount;
    private final float threshold;

    public PromotionalRule(String productCode, float discount, float threshold) {
        this.promotionType = PromotionType.PRODUCT;
        this.productCode = productCode;
        this.discount = discount;
        this.threshold = threshold;
    }

    public PromotionalRule(float discount, float threshold) {
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

    public float getDiscount() {
        return discount;
    }

    public float getThreshold() {
        return threshold;
    }
}
