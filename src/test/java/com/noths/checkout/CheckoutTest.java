package com.noths.checkout;

import com.noths.checkout.domain.Product;
import com.noths.checkout.domain.PromotionalRule;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {


    @Test
    void testThatICanCheckoutItems() {
        //given promotional rules
        List<PromotionalRule> promotionalRules = new ArrayList<>();
        //TODO provide rules

        //and checkout class
        Checkout checkout = new Checkout(promotionalRules);

        // and scanned items
        checkout.scan(new Product("001","Travel Card Holder", 9.25));
        checkout.scan(new Product("002","Personalised cufflinks", 45));
        checkout.scan(new Product("003","Kids T-shirt", 19.95));

        //when I retrieve the total
        Double price = checkout.total();

        // then I get expected value
        assertEquals(66.78,price);
    }



}