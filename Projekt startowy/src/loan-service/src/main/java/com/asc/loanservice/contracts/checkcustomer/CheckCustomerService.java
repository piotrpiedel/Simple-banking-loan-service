package com.asc.loanservice.contracts.checkcustomer;

import com.asc.loanservice.domain.CustomerCheck;

public class CheckCustomerService implements CustomerCheck {
    CheckCustomerClient checkCustomerClient;

    public CheckCustomerService(CheckCustomerClient checkCustomerClient) {
        this.checkCustomerClient = checkCustomerClient;
    }

    @Override
    public boolean checkCustomerIfNotOnDebtorList(String customerTaxId) {
        CustomerCheckResultDto customerCheckResultD =
                checkCustomerClient.checkCustomerIfExistOnDebtorsList(customerTaxId);
        return customerCheckResultD.getIsRegisteredDebtor();
    }
}
