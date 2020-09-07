package com.asc.loanservice.contracts.customercheck;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class CustomerCheckResultDto {
    private final String customerTaxId;
    private final Boolean isRegisteredDebtor;
}
