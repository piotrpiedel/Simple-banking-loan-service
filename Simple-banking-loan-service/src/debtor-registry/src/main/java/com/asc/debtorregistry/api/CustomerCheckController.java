package com.asc.debtorregistry.api;

import com.asc.debtorregistry.contracts.CustomerCheckResultDto;
import com.asc.debtorregistry.domain.CustomerCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customercheck")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerCheckController {
    private final CustomerCheckService customerCheckService;

    @GetMapping("/{customerTaxId}")
    public CustomerCheckResultDto check(@PathVariable("customerTaxId") String customerTaxId){
        return customerCheckService.checkCustomer(customerTaxId);
    }
}
