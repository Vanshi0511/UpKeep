package com.example.upkeep.models;

public class AddPaymentModel {

    private String card;
    private String cvc;
    private String expiry;
    private String zip;

    public AddPaymentModel(String card, String cvc, String expiry, String zip) {
        this.card = card;
        this.cvc = cvc;
        this.expiry = expiry;
        this.zip = zip;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
