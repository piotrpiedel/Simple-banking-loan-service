package com.asc.loanservice.domain;

import com.asc.loanservice.domain.LoanRequest.LoanRequestStatus;

public class LoanRequestValidator {

    public LoanRequestStatus validate(LoanRequest loanRequest) {

        return LoanRequestStatus.REJECTED;
    }

}

