package com.noths.checkout;

import com.noths.checkout.domain.Product;
import com.noths.checkout.domain.PromotionalRule;

import java.util.List;

public class Checkout {

    private final List<PromotionalRule> promotionalRules;

    public Checkout(List<PromotionalRule> promotionalRules) {
        this.promotionalRules=promotionalRules;
    }

    public void scan(Product productCode) {

    }

    public Double total() {
        return null;
    }
}
