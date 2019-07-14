package com.noths.checkout.domain;

public class ScannedProduct extends Product{
    private int nbItems;

    public ScannedProduct(String productCode, String name, Float price, int nbItems) {
        super(productCode, name, price);
        this.nbItems=nbItems;
    }

    public int getNbItems() {
        return nbItems;
    }

    public void setNbItems(int nbItems) {
        this.nbItems = nbItems;
    }
}
