package com.asc.loanservice.contracts.checkcustomer;

import com.asc.loanservice.domain.CustomerCheck;
import org.springframework.stereotype.Service;

@Service
public class CheckCustomerService implements CustomerCheck {
    CheckCustomerClient checkCustomerClient;

    public CheckCustomerService(CheckCustomerClient checkCustomerClient) {
        this.checkCustomerClient = checkCustomerClient;
    }

    @Override
    public boolean isCustomerDebtor(String customerTaxId) {
        CustomerCheckResultDto customerCheckResultD =
                checkCustomerClient.checkCustomerIfExistOnDebtorsList(customerTaxId);
        return customerCheckResultD.getIsRegisteredDebtor();
    }
}
