package com.petrov.dimitar.zopa;

public class Offer implements Comparable<Offer> {

    private Integer amount;
    private Double rate;

    public Offer(Integer amount, Double rate) {
        this.amount = amount;
        this.rate = rate;
    }

    public Integer getAmount() {
        return amount;
    }

    public Double getRate() {
        return rate;
    }

    @Override
    public int compareTo(Offer offer) {
        return this.rate.compareTo(offer.getRate());
    }
}
