package com.petrov.dimitar.zopa;

import java.math.BigDecimal;

public class Quote {

    private Integer requestedAmount;
    private BigDecimal rate;
    private BigDecimal monthlyRepayment;
    private BigDecimal totalRepayment;

    public Quote(Integer requestedAmount,
                 BigDecimal rate,
                 BigDecimal monthlyRepayment,
                 BigDecimal totalRepayment) {
        this.requestedAmount = requestedAmount;
        this.monthlyRepayment = monthlyRepayment;
        this.rate = rate;
        this.totalRepayment = totalRepayment;
    }


    @Override
    public String toString() {
        return String.format("Requested amount: £%d\n" +
                "Rate: %.1f%%\n" +
                "Monthly repayment: £%.2f\n" +
                "Total repayment: £%.2f",
                requestedAmount,
                rate.multiply(new BigDecimal(100)).doubleValue(),
                monthlyRepayment.doubleValue(),
                totalRepayment.doubleValue());
    }

}
