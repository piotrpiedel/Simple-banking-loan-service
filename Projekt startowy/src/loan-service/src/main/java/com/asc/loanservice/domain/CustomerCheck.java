package com.asc.loanservice.domain;

import org.springframework.stereotype.Service;

public interface CustomerCheck {
    boolean checkCustomerIfNotOnDebtorList(String customerTaxId);
}
