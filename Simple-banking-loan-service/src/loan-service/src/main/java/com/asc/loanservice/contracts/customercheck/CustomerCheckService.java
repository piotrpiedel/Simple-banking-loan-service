package com.asc.loanservice.contracts.customercheck;

import com.asc.loanservice.domain.CustomerCheck;
import org.springframework.stereotype.Service;

@Service
public final class CustomerCheckService implements CustomerCheck {
    CustomerCheckClient customerCheckClient;

    public CustomerCheckService(CustomerCheckClient customerCheckClient) {
        this.customerCheckClient = customerCheckClient;
    }

    @Override
    public boolean isCustomerDebtor(String customerTaxId) {
        CustomerCheckResultDto customerCheckResultD =
                customerCheckClient.checkCustomerIfExistOnDebtorsList(customerTaxId);
        return customerCheckResultD.getIsRegisteredDebtor();
    }
}
