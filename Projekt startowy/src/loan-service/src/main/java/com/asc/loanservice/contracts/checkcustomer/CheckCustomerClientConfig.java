package com.asc.loanservice.contracts.checkcustomer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CheckCustomerClientConfig {
    public final String API_ENDPOINT = "localhost:8090/api/customercheck";
    public final String CHECK_CUSTOMER_ENDPOINT = API_ENDPOINT + "/{customerTaxId}";
}
