package com.asc.loanservice.contracts.checkcustomer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCheckResultDto {
    private String customerTaxId;
    private Boolean isRegisteredDebtor;
}
