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
        //Given checkout class without promotional rules
        Checkout checkout = new Checkout(null);

        // and scanned items
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));
        checkout.scan(new Product("002","Personalised cufflinks", 45.0f));
        checkout.scan(new Product("003","Kids T-shirt", 19.95f));

        //when I retrieve the total
        Float price = checkout.total();

        // then I get expected value
        assertEquals(74.2f,price);
    }
    @Test
    void testThatICanCheckoutWithAnEmptyBasket() {
        //Given checkout class without promotional rules
        Checkout checkout = new Checkout(null);

        // and no scanned item

        //when I retrieve the total
        Float price = checkout.total();

        // then I get expected value
        assertEquals(0f,price);
    }

    @Test
    void testThatICanCheckoutItemsWithProductPromotionalRules() {
        //given promotional rules
        List<PromotionalRule> promotionalRules = new ArrayList<>();
        promotionalRules.add(new PromotionalRule("001",8.50f,2.0f));

        //and checkout class
        Checkout checkout = new Checkout(promotionalRules);

        // and scanned items
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));
        checkout.scan(new Product("002","Personalised cufflinks", 45.0f));
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));
        checkout.scan(new Product("003","Kids T-shirt", 19.95f));

        //when I retrieve the total
        Float price = checkout.total();

        // then I get expected value
        assertEquals(81.95f,price);
    }

    @Test
    void testThatICanCheckoutItemsWithTotalPromotionalRule() {
        //given promotional rules
        List<PromotionalRule> promotionalRules = new ArrayList<>();
        promotionalRules.add(new PromotionalRule(0.10f,60.0f));

        //and checkout class
        Checkout checkout = new Checkout(promotionalRules);

        // and scanned items
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));
        checkout.scan(new Product("002","Personalised cufflinks", 45.0f));
        checkout.scan(new Product("003","Kids T-shirt", 19.95f));

        //when I retrieve the total
        Float price = checkout.total();

        // then I get expected value
        assertEquals(66.78f,price);
    }

    @Test
    void testThatICanCheckoutItemsWithCompetingTotalPromotionalRules() {
        //given promotional rules
        List<PromotionalRule> promotionalRules = new ArrayList<>();
        promotionalRules.add(new PromotionalRule(0.10f,60.0f));
        promotionalRules.add(new PromotionalRule(0.15f,70.0f));

        //and checkout class
        Checkout checkout = new Checkout(promotionalRules);

        // and scanned items
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));
        checkout.scan(new Product("002","Personalised cufflinks", 45.0f));
        checkout.scan(new Product("003","Kids T-shirt", 19.95f));

        //when I retrieve the total
        Float price = checkout.total();

        // then I get expected value
        assertEquals(63.07f,price);
    }


    @Test
    void testThatICanCheckoutItemsWithPromotionalRules() {
        //given promotional rules
        List<PromotionalRule> promotionalRules = new ArrayList<>();
        promotionalRules.add(new PromotionalRule(0.10f,60.0f));
        promotionalRules.add(new PromotionalRule("001",8.50f,2.0f));

        //and checkout class
        Checkout checkout = new Checkout(promotionalRules);

        // and scanned items
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));
        checkout.scan(new Product("003","Kids T-shirt", 19.95f));
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));

        //when I retrieve the total
        Float price = checkout.total();

        // then I get expected value
        assertEquals(36.95f,price);
    }


    @Test
    void testThatICanCheckoutItemsWithPromotionalRules2() {
        //given promotional rules
        List<PromotionalRule> promotionalRules = new ArrayList<>();
        promotionalRules.add(new PromotionalRule(0.10f,60.0f));
        promotionalRules.add(new PromotionalRule("001",8.50f,2.0f));

        //and checkout class
        Checkout checkout = new Checkout(promotionalRules);

        // and scanned items
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));
        checkout.scan(new Product("002","Personalised cufflinks", 45.0f));
        checkout.scan(new Product("001","Travel Card Holder", 9.25f));
        checkout.scan(new Product("003","Kids T-shirt", 19.95f));

        //when I retrieve the total
        Float price = checkout.total();

        // then I get expected value
        assertEquals(73.76f,price);
    }


}