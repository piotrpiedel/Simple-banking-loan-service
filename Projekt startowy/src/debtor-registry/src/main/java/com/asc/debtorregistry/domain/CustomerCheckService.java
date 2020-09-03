package com.asc.debtorregistry.domain;

import com.asc.debtorregistry.contracts.CustomerCheckResultDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerCheckService {
    private final List<String> blackList = Arrays.asList(
        "35062600206", "40080200695", "01222815571", "51092513727"
    );

    public CustomerCheckResultDto checkCustomer(String customerTaxId){
        if (blackList.contains(customerTaxId)){
            return CustomerCheckResultDto.debtorCustomer(customerTaxId);
        } else {
            return CustomerCheckResultDto.notDebtorCustomer(customerTaxId);
        }
    }
}
