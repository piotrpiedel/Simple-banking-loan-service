package com.asc.loanservice.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class LoanRequestValidationRulesTest {

    public static final double CREDIT_INTERESTS_RATE_YEARLY = 0.04d;

    @Mock
    CustomerCheck customerCheck;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void clientAgeLesserThanSixtyFiveWhenLoanRepaid() {
        //given
        int numberOfInstallments = 32;
        LoanRequest loanRequest = LoanRequest
                .builder()
                .numberOfInstallments(numberOfInstallments)
                .firstInstallmentDate(LocalDate.of(2020, 8, 1)) // +32 months -> Apr 1, 2023
                .customerBirthday(LocalDate.of(2000, 5, 1))
                .build();

        LoanRequestValidationRules builder = new LoanRequestValidationRules(
                loanRequest, customerCheck, CREDIT_INTERESTS_RATE_YEARLY);
        //when
        List<Predicate<LoanRequest>> loanValidationRules = builder
                .clientAgeLesserThanSixtyFiveWhenLoanRepaid()
                .build();
        //then
        assertTrue(loanValidationRules
                .stream()
                .allMatch(loanRequestPredicate -> loanRequestPredicate.test(loanRequest)));
    }

    @Test
    public void clientOlderThanSixtyFiveWhenLoanRepaid() {
        //given
        int numberOfInstallments = 785;
        LoanRequest loanRequest = LoanRequest
                .builder()
                .numberOfInstallments(numberOfInstallments)
                .firstInstallmentDate(LocalDate.of(2020, 8, 1)) // +785 months -> Jan 1, 2086
                .customerBirthday(LocalDate.of(2000, 5, 1))
                .build();

        LoanRequestValidationRules builder = new LoanRequestValidationRules(
                loanRequest, customerCheck, CREDIT_INTERESTS_RATE_YEARLY);
        //when
        List<Predicate<LoanRequest>> loanValidationRules = builder
                .clientAgeLesserThanSixtyFiveWhenLoanRepaid()
                .build();
        //then
        assertFalse(loanValidationRules
                .stream()
                .allMatch(loanRequestPredicate -> loanRequestPredicate.test(loanRequest)));
    }

    @Test
    public void monthlyRatesBiggerThanMaximumPercentageToIncome() {
        //given
        int numberOfInstallments = 72; // 6 years
        LoanRequest loanRequest = LoanRequest
                .builder()
                .numberOfInstallments(numberOfInstallments)
                .loanAmount(BigDecimal.valueOf(100000))
                .customerMonthlyIncome(BigDecimal.valueOf(5000))
                .build();

        LoanRequestValidationRules builder = new LoanRequestValidationRules(
                loanRequest, customerCheck, CREDIT_INTERESTS_RATE_YEARLY);
        //when
        List<Predicate<LoanRequest>> loanValidationRules = builder
                .monthlyRatesMaximumPercentageToIncome()
                .build();
        //then
        //fixed installment monthly rate with this conditions will be 1562,7232 / 5000 ~ 31% != <15%
        assertFalse(loanValidationRules
                .stream()
                .allMatch(loanRequestPredicate -> loanRequestPredicate.test(loanRequest)));
    }

    @Test
    public void monthlyRatesLessOrEqualThanMaximumPercentageToIncome() {
        //given
        int numberOfInstallments = 12;
        LoanRequest loanRequest = LoanRequest
                .builder()
                .numberOfInstallments(numberOfInstallments)
                .loanAmount(BigDecimal.valueOf(10000))
                .customerMonthlyIncome(BigDecimal.valueOf(5679.06))
                .build();

        LoanRequestValidationRules builder = new LoanRequestValidationRules(
                loanRequest, customerCheck, CREDIT_INTERESTS_RATE_YEARLY);
        //when
        List<Predicate<LoanRequest>> loanValidationRules = builder
                .monthlyRatesMaximumPercentageToIncome()
                .build();
        //then
        //fixed installment monthly rate with this conditions will be 851,86 / 5679,06 ~ 15% <= 15%
        assertTrue(loanValidationRules
                .stream()
                .allMatch(loanRequestPredicate -> loanRequestPredicate.test(loanRequest)));
    }

    @Test
    public void clientNotOnDebtorsList() {
        //given
        LoanRequest loanRequest = LoanRequest
                .builder()
                .customerTaxId("99999")
                .build();

        when(customerCheck.isCustomerDebtor("99999"))
                .thenReturn(false);

        LoanRequestValidationRules builder = new LoanRequestValidationRules(
                loanRequest, customerCheck, CREDIT_INTERESTS_RATE_YEARLY);
        //when
        List<Predicate<LoanRequest>> loanValidationRules = builder
                .clientNotOnDebtorsList()
                .build();
        //then
        assertTrue(loanValidationRules
                .stream()
                .allMatch(loanRequestPredicate -> loanRequestPredicate.test(loanRequest)));
    }

    @Test
    public void clientOnDebtorsList() {
        //given
        LoanRequest loanRequest = LoanRequest
                .builder()
                .customerTaxId("99999")
                .build();

        when(customerCheck.isCustomerDebtor("99999"))
                .thenReturn(true);

        LoanRequestValidationRules builder = new LoanRequestValidationRules(
                loanRequest, customerCheck, CREDIT_INTERESTS_RATE_YEARLY);
        //when
        List<Predicate<LoanRequest>> loanValidationRules = builder
                .clientNotOnDebtorsList()
                .build();
        //then
        assertFalse(loanValidationRules
                .stream()
                .allMatch(loanRequestPredicate -> loanRequestPredicate.test(loanRequest)));
    }
}