package com.asc.loanservice.contracts.checkcustomer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CheckCustomerClient {

    private final RestTemplate rest;

    public CheckCustomerClient() {
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
