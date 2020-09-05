package com.asc.loanservice.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LoanRequestValidationRules {

    public static final int MAX_CLIENT_AGE_IN_YEARS = 65;
    public static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
    public static final double MAXIMUM_RATE_TO_INCOME_PERCENTAGE = 0.15;

    private final List<Predicate<LoanRequest>> loanRequestValidationRules;

    private final LoanRequest loanRequest;
    private final CustomerCheck customerCheck;
    private final double creditInterestsRateYearly;

    LoanRequestValidationRules(
            LoanRequest loanRequest, CustomerCheck customerCheck,
            double creditInterestsRateYearly) {
        this.loanRequest = loanRequest;
        this.customerCheck = customerCheck;
        this.creditInterestsRateYearly = creditInterestsRateYearly;
        loanRequestValidationRules = new ArrayList<>();
    }

    public LoanRequestValidationRules clientAgeLesserThanSixtyFiveWhenLoanRepaid() {
        int numberOfLoanMonths = loanRequest.getNumberOfInstallments();
        LocalDate firstInstallmentDate = loanRequest.getFirstInstallmentDate();
        int yearWhenLoanWillBeRepaid = firstInstallmentDate
                .plusMonths(numberOfLoanMonths)
                .getYear();

        int customerYearOfBirth = loanRequest.getCustomerBirthday().getYear();
        int customerAgeInYearsWhenLoanWillBeRepaid = yearWhenLoanWillBeRepaid - customerYearOfBirth;
        loanRequestValidationRules
                .add(rule -> customerAgeInYearsWhenLoanWillBeRepaid <= MAX_CLIENT_AGE_IN_YEARS);
        return this;
    }

    /*
    Fixed installment algorithm = c * y^n * (y‑1) / (y^n‑1)
        c – loan amount
        n – number of installments
        y = 1 + (r / 12), where r is credit interests rate yearly
    */
    public LoanRequestValidationRules monthlyRatesMaximumPercentageToIncome() {
        int numberOfInstallments = loanRequest.getNumberOfInstallments();
        BigDecimal loanAmount = loanRequest.getLoanAmount();
        BigDecimal monthlyRateValue = calculateMonthlyRateValue(numberOfInstallments, loanAmount);

        BigDecimal customerMonthlyIncome = loanRequest.getCustomerMonthlyIncome();
        loanRequestValidationRules
                .add(rule -> monthlyRateValue
                        .divide(customerMonthlyIncome, RoundingMode.HALF_UP)
                        .compareTo(BigDecimal.valueOf(MAXIMUM_RATE_TO_INCOME_PERCENTAGE)) < 0);
        return this;
    }

    private BigDecimal calculateMonthlyRateValue(int numberOfInstallments, BigDecimal loanAmount) {
        BigDecimal yConstant = BigDecimal
                .valueOf(1 + (creditInterestsRateYearly / NUMBER_OF_MONTHS_IN_YEAR))
                .setScale(3, RoundingMode.HALF_UP);

        return loanAmount
                .multiply(yConstant
                        .pow(numberOfInstallments))
                .multiply(yConstant
                        .subtract(BigDecimal.valueOf(1)))
                .divide(yConstant
                        .pow(numberOfInstallments - 1), RoundingMode.HALF_UP);
    }

    public LoanRequestValidationRules clientNotOnDebtorsList() {
        loanRequestValidationRules
                .add(rule -> customerCheck.checkCustomerIfNotOnDebtorList(loanRequest));
        return this;
    }

    public List<Predicate<LoanRequest>> build() {
        return loanRequestValidationRules;
    }
}
