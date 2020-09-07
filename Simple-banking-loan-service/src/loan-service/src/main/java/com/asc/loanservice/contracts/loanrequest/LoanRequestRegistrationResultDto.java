package com.asc.loanservice.contracts.loanrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public final class LoanRequestRegistrationResultDto {
    private String loanRequestNumber;
    private LoanRequestEvaluationResult evaluationResult;
}
