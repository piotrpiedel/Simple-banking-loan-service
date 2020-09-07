package com.asc.loanservice.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

final class FixedRateMonthly {

    public static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
    private final BigDecimal rateValueMonthly;

    private FixedRateMonthly(BigDecimal rateValueMonthly) {
        this.rateValueMonthly = rateValueMonthly;
    }

    /*
    Fixed installment algorithm = P (r(1+r)^n) / ((1+r)^n-1)
        P – loan amount - Principal
        n – number of installments
        r = (R / 12), where R is credit interests rate yearly
    */
    public static FixedRateMonthly of(
            BigDecimal loanAmount,
            int numberOfInstallments,
            double creditInterestsRateYearly) {

        BigDecimal monthlyInterestRate = BigDecimal
                .valueOf(creditInterestsRateYearly / NUMBER_OF_MONTHS_IN_YEAR)
                .setScale(4, RoundingMode.HALF_UP);

        BigDecimal onePlusMonthlyInterestRate = BigDecimal.valueOf(1).add(monthlyInterestRate);

        BigDecimal onePlusMonthlyInterestByPow = onePlusMonthlyInterestRate
                .pow(numberOfInstallments)
                .setScale(4, RoundingMode.HALF_UP);

        BigDecimal numerator = loanAmount
                .multiply(monthlyInterestRate
                        .multiply(onePlusMonthlyInterestByPow))
                .setScale(4, RoundingMode.HALF_UP);
        BigDecimal denominator = (
                onePlusMonthlyInterestByPow.subtract(BigDecimal.valueOf(1)))
                .setScale(4, RoundingMode.HALF_UP);

        BigDecimal rate = numerator
                .divide(denominator, RoundingMode.HALF_UP);

        return new FixedRateMonthly(rate);
    }

    public BigDecimal getAsBigDecimal() {
        return rateValueMonthly;
    }
}
