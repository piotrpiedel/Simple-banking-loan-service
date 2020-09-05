package com.asc.loanservice.domain;

public interface CustomerCheck {
    boolean checkCustomerIfNotOnDebtorList(String customerTaxId);
}
