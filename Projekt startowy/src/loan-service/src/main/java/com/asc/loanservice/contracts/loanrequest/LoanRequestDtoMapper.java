package com.asc.loanservice.contracts.loanrequest;

import com.asc.loanservice.domain.LoanRequest;

public class LoanRequestDtoMapper {

    public LoanRequest asEntity(LoanRequestDto loanRequestDto) {
        return new LoanRequest();
    }

    public LoanRequestDto asDto(LoanRequest loanRequest) {
        return new LoanRequestDto();
    }
}
