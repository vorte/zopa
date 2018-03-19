package com.petrov.dimitar.zopa;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LendingService {

    public Optional<Quote> borrow(int amount, List<Offer> market) {
        Collections.sort(market);
        List<Offer> quoteOffers = new ArrayList<>();
        int remainingAmount = amount;

        while (remainingAmount > 0 && !market.isEmpty()) {
            Offer offer = market.get(0);
            if (offer.getAmount() > remainingAmount) {
                quoteOffers.add(new Offer(remainingAmount, offer.getRate()));
                remainingAmount = 0;
            } else {
                quoteOffers.add(offer);
                remainingAmount -= offer.getAmount();
                market.remove(0);
            }
        }

        if (remainingAmount > 0) {
            return Optional.empty();
        }

        return Optional.of(calculateQuote(quoteOffers, amount));
    }

    private Quote calculateQuote(List<Offer> offers, int requestedAmount) {
        BigDecimal rate = calculateRate(offers, requestedAmount);

        BigDecimal monthlyRate = rate.divide(new BigDecimal(12), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal divisor = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(
                BigDecimal.ONE.add(monthlyRate).pow(36), 10, BigDecimal.ROUND_HALF_UP));

        BigDecimal monthlyRepayment = new BigDecimal(requestedAmount).multiply(monthlyRate)
                .divide(divisor, 10, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalRepayment = new BigDecimal(36).multiply(monthlyRepayment);

        return new Quote(requestedAmount, rate, monthlyRepayment, totalRepayment);
    }

    private BigDecimal calculateRate(List<Offer> offers, int requestedAmount) {
        BigDecimal weightedAverage = new BigDecimal(0.0);
        for (Offer offer: offers) {
            BigDecimal weight = new BigDecimal(offer.getAmount()).divide(new BigDecimal(requestedAmount), 10,
                    BigDecimal.ROUND_HALF_UP);
            weightedAverage = weightedAverage.add(weight.multiply(new BigDecimal(offer.getRate())));
        }

        return weightedAverage;
    }

}
