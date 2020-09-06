package com.asc.loanservice.contracts.checkcustomer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CheckCustomerClientConfig {
    //fields must be static despite utility class due to bug in Lombok
    public static final String API_ENDPOINT = "localhost:8090/api/customercheck";
    public static final String CHECK_CUSTOMER_ENDPOINT = API_ENDPOINT + "/{customerTaxId}";
}
