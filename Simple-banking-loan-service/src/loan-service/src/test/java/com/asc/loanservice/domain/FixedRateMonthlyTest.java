package com.asc.loanservice.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class FixedRateMonthlyTest {

    @Test
    public void ofLowLoanValue() {
        //given
        BigDecimal loanAmount = BigDecimal.valueOf(3500);
        int numberOfInstallments = 36;
        double creditInterestsRateYearly = 0.04;
        //when
        FixedRateMonthly  fixedRateMonthly = FixedRateMonthly
                .of(loanAmount, numberOfInstallments, creditInterestsRateYearly);
        //then
        double valueFromInternetCalculator = 103.29;
        assertEquals(
                BigDecimal.valueOf(valueFromInternetCalculator),
                fixedRateMonthly
                        .getAsBigDecimal()
                        .setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void ofHighLoanValue() {
        //given
        BigDecimal loanAmount = BigDecimal.valueOf(100000);
        int numberOfInstallments = 72;
        double creditInterestsRateYearly = 0.04;
        //when
        FixedRateMonthly fixedRateMonthly = FixedRateMonthly
                .of(loanAmount, numberOfInstallments, creditInterestsRateYearly);
        //then
        double valueFromInternetCalculator = 1562.72;
        assertEquals(
                BigDecimal.valueOf(valueFromInternetCalculator),
                fixedRateMonthly
                        .getAsBigDecimal()
                        .setScale(2, RoundingMode.HALF_UP));
    }
}