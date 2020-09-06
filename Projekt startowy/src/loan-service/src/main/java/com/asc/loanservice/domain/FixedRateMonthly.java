package com.asc.loanservice.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FixedRateMonthly {

    public static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
    private final BigDecimal rateValueMonthly;

    private FixedRateMonthly(BigDecimal rateValueMonthly) {
        this.rateValueMonthly = rateValueMonthly;
    }

    /*
    Fixed installment algorithm = c * y^n * (y‑1) / (y^n‑1)
        c – loan amount
        n – number of installments
        y = 1 + (r / 12), where r is credit interests rate yearly
    */
    public static FixedRateMonthly of(
            BigDecimal loanAmount,
            int numberOfInstallments,
            double creditInterestsRateYearly) {

        BigDecimal yConstant = BigDecimal
                .valueOf(1 + (creditInterestsRateYearly / NUMBER_OF_MONTHS_IN_YEAR))
                .setScale(3, RoundingMode.HALF_UP);

        BigDecimal rate =
                loanAmount
                        .multiply(yConstant
                                .pow(numberOfInstallments))
                        .multiply(yConstant
                                .subtract(BigDecimal.valueOf(1)))
                        .divide(yConstant
                                .pow(numberOfInstallments - 1), RoundingMode.HALF_UP);
        return new FixedRateMonthly(rate);
    }

    public BigDecimal getAsBigDecimal() {
        return rateValueMonthly;
    }
}
