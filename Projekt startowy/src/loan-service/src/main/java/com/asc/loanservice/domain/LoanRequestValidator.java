package com.asc.loanservice.domain;

import com.asc.loanservice.domain.LoanRequest.LoanRequestStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LoanRequestValidator {

    public static final double CREDIT_INTERESTS_RATE_YEARLY = 0.04d;
    private final CustomerCheck customerCheck;

    public LoanRequestValidator(CustomerCheck customerCheck) {
        this.customerCheck = customerCheck;
    }

    public LoanRequestStatus validate(LoanRequest loanRequest) {
        LoanRequestValidationRules builder = new LoanRequestValidationRules(
                loanRequest,
                customerCheck,
                CREDIT_INTERESTS_RATE_YEARLY);

        List<Predicate<LoanRequest>> loanValidationRules = builder
                .clientAgeLesserThanSixtyFiveWhenLoanRepaid()
                .monthlyRatesMaximumPercentageToIncome()
                .clientNotOnDebtorsList()
                .build();

        List<LoanRequest> list =
                Stream.of(loanRequest)
                        .filter(filterWithValidationRules(loanRequest, loanValidationRules))
                        .collect(Collectors.toList());

        if (!list.isEmpty()) {
            return LoanRequestStatus.APPROVED;
        }
        return LoanRequestStatus.REJECTED;
    }

    private Predicate<LoanRequest> filterWithValidationRules(
            LoanRequest loanRequest,
            List<Predicate<LoanRequest>> loanValidationRules) {
        return p ->
                loanValidationRules
                        .stream()
                        .allMatch(loanRequestPredicate -> loanRequestPredicate
                                .test(loanRequest));
    }

}

