package com.asc.loanservice.contracts.loanrequest;

import com.asc.loanservice.domain.LoanRequest;

//TODO: implement
public class LoanRequestMapper {

    public LoanRequest asEntity(LoanRequestDto loanRequestDto) {
        return new LoanRequest();
    }

    public LoanRequestDto asDto(LoanRequest loanRequest) {
        return new LoanRequestDto();
    }

    public LoanRequestDataDto asDataDto(LoanRequest loanRequest) {
        return new LoanRequestDataDto();
    }
}