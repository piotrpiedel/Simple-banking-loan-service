package com.asc.loanservice.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LoanRequestValidationRules {

    public static final int MAX_CLIENT_AGE_IN_YEARS = 65;
    public static final double MAXIMUM_RATE_TO_INCOME_PERCENTAGE = 0.15;

    private final List<Predicate<LoanRequest>> validationRules;

    private final LoanRequest loanRequest;
    private final CustomerCheck customerCheck;
    private final double creditInterestsRateYearly;

    LoanRequestValidationRules(
            LoanRequest loanRequest,
            CustomerCheck customerCheck,
            double creditInterestsRateYearly) {
        this.loanRequest = loanRequest;
        this.customerCheck = customerCheck;
        this.creditInterestsRateYearly = creditInterestsRateYearly;
        validationRules = new ArrayList<>();
    }

    public LoanRequestValidationRules clientAgeLesserThanSixtyFiveWhenLoanRepaid() {
        int yearWhenLoanWillBeRepaid = getYearWhenLoanWillBeRepaid();
        int customerYearOfBirth = getCustomerYearOfBirth();

        int customerAgeInYears = calculateCustomerAgeWhenLoanWillBeRepaid(
                yearWhenLoanWillBeRepaid, customerYearOfBirth);
        validationRules
                .add(rule -> customerAgeInYears <= MAX_CLIENT_AGE_IN_YEARS);
        return this;
    }

    private int getYearWhenLoanWillBeRepaid() {
        int numberOfLoanMonths = loanRequest.getNumberOfInstallments();
        LocalDate firstInstallmentDate = loanRequest.getFirstInstallmentDate();
        return firstInstallmentDate
                .plusMonths(numberOfLoanMonths)
                .getYear();
    }

    private int getCustomerYearOfBirth() {
        return loanRequest
                .getCustomerBirthday()
                .getYear();
    }

    private int calculateCustomerAgeWhenLoanWillBeRepaid(
            int yearWhenLoanWillBeRepaid, int customerYearOfBirth) {
        return yearWhenLoanWillBeRepaid - customerYearOfBirth;
    }

    public LoanRequestValidationRules monthlyRatesMaximumPercentageToIncome() {
        int numberOfInstallments = loanRequest.getNumberOfInstallments();
        BigDecimal loanAmount = loanRequest.getLoanAmount();
        FixedRateMonthly fixedRateMonthly = FixedRateMonthly
                .of(loanAmount, numberOfInstallments, creditInterestsRateYearly);

        BigDecimal monthlyRateValue = fixedRateMonthly.getAsBigDecimal();

        BigDecimal customerMonthlyIncome = loanRequest.getCustomerMonthlyIncome();
        validationRules
                .add(monthlyToIncomePercentage(monthlyRateValue, customerMonthlyIncome));
        return this;
    }

    private Predicate<LoanRequest> monthlyToIncomePercentage(
            BigDecimal monthlyRateValue, BigDecimal customerMonthlyIncome) {
        return rule -> monthlyRateValue
                .divide(customerMonthlyIncome, RoundingMode.HALF_UP)
                .compareTo(BigDecimal.valueOf(MAXIMUM_RATE_TO_INCOME_PERCENTAGE)) <= 0;
    }

    public LoanRequestValidationRules clientNotOnDebtorsList() {
        validationRules
                .add(rule -> !customerCheck
                        .isCustomerDebtor(loanRequest.getCustomerTaxId()));
        return this;
    }

    public List<Predicate<LoanRequest>> build() {
        return validationRules;
    }
}
