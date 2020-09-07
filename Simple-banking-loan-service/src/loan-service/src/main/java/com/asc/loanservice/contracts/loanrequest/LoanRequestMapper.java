package com.asc.loanservice.contracts.loanrequest;

import com.asc.loanservice.domain.LoanRequest;
import org.springframework.stereotype.Component;

@Component
public final class LoanRequestMapper {

    public LoanRequest asEntity(LoanRequestDto loanRequestDto) {
        return LoanRequest.builder()
                .customerName(loanRequestDto.getCustomerName())
                .customerBirthday(loanRequestDto.getCustomerBirthday())
                .customerTaxId(loanRequestDto.getCustomerTaxId())
                .customerMonthlyIncome(loanRequestDto.getCustomerMonthlyIncome())
                .loanAmount(loanRequestDto.getLoanAmount())
                .numberOfInstallments(loanRequestDto.getNumberOfInstallments())
                .firstInstallmentDate(loanRequestDto.getFirstInstallmentDate())
                .build();
    }

    public LoanRequestDataDto asDataDto(LoanRequest loanRequest) {
        return LoanRequestDataDto.builder()
                .loanRequestNumber(String.valueOf(loanRequest.getLoanRequestNumber()))
                .customerName(loanRequest.getCustomerName())
                .customerBirthday(loanRequest.getCustomerBirthday())
                .customerTaxId(loanRequest.getCustomerTaxId())
                .customerMonthlyIncome(loanRequest.getCustomerMonthlyIncome())
                .loanAmount(loanRequest.getLoanAmount())
                .numberOfInstallments(loanRequest.getNumberOfInstallments())
                .firstInstallmentDate(loanRequest.getFirstInstallmentDate())
                .evaluationResult(LoanRequestEvaluationResult.valueOf(loanRequest
                        .getEvaluationResult().toString()))
                .registrationDate(loanRequest.getRegistrationDate())
                .build();
    }
}
