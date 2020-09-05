package com.asc.loanservice.contracts.checkcustomer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CheckCustomerClient {

    private final CheckCustomerClient checkCustomerClient;
    private final RestTemplate rest;

    public CheckCustomerClient(CheckCustomerClient checkCustomerClient) {
        this.checkCustomerClient = checkCustomerClient;
        rest = new RestTemplate();
    }

    public CustomerCheckResultDto checkCustomerIfExistOnDebtorsList(String customerTaxId) {
        ResponseEntity<CustomerCheckResultDto> result = rest.exchange(
                "http://bykowski.pl/materials/HttpExample.php?name=Przemek",
                HttpMethod.GET, HttpEntity.EMPTY,
                CustomerCheckResultDto.class);

        if (result.getBody() != null) {
            return result.getBody();
        }
        throw new IllegalArgumentException("Incorrect customer tax id: " + customerTaxId);
    }
}
