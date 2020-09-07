package com.asc.loanservice.contracts.customercheck;

import lombok.experimental.UtilityClass;

@UtilityClass
final class CustomerCheckClientConfig {
    //fields must be static despite utility class due to bug in Lombok
    public static final String API_ENDPOINT = "http://localhost:8090/api/customercheck";
    public static final String CHECK_CUSTOMER_ENDPOINT = API_ENDPOINT + "/{customerTaxId}";
}
