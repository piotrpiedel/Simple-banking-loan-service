package com.asc.loanservice.contracts.customercheck;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.asc.loanservice.contracts.customercheck.CustomerCheckClientConfig.CHECK_CUSTOMER_ENDPOINT;

@Component
final class CustomerCheckClient {

    private final RestTemplate rest;

    CustomerCheckClient() {
        rest = new RestTemplate();
    }

    public CustomerCheckResultDto checkCustomerIfExistOnDebtorsList(String customerTaxId) {
        ResponseEntity<CustomerCheckResultDto> result = rest
                .exchange(
                        CHECK_CUSTOMER_ENDPOINT,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        CustomerCheckResultDto.class,
                        customerTaxId);
        if (result.getBody() != null) {
            return result.getBody();
        }
        return new CustomerCheckResultDtoNull();
    }
}
