package com.asc.loanservice.domain;

import com.asc.loanservice.domain.LoanRequest.LoanRequestStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
final class LoanRequestValidator {

    private final CustomerCheck customerCheck;

    LoanRequestValidator(CustomerCheck customerCheck) {
        this.customerCheck = customerCheck;
    }

    public LoanRequestStatus validate(LoanRequest loanRequest, double creditInterestYearly) {
        LoanRequestValidationRules builder = new LoanRequestValidationRules(
                loanRequest, customerCheck, creditInterestYearly);

        List<Predicate<LoanRequest>> loanValidationRules = builder
                .clientAgeLesserThanSixtyFiveWhenLoanRepaid()
                .monthlyRatesMaximumPercentageToIncome()
                .clientNotOnDebtorsList()
                .build();

        boolean isValid = loanValidationRules
                .stream()
                .allMatch(loanRequestPredicate -> loanRequestPredicate.test(loanRequest));

        if (isValid) {
            return LoanRequestStatus.APPROVED;
        }
        return LoanRequestStatus.REJECTED;
    }
}

