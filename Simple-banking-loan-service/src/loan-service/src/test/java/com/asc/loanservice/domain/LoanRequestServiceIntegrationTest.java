package com.asc.loanservice.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LoanRequestServiceIntegrationTest {

    @Autowired
    LoanRequestRepository loanRequestRepository;

    @Autowired
    LoanRequestService loanRequestService;

    @Test
    public void getLoanRequest() {
        // given
        LoanRequest loanRequest = LoanRequest
                .builder()
                .loanRequestNumber(1L)
                .customerName("Pawel")
                .build();
        loanRequest = loanRequestRepository.save(loanRequest);
        // when
        LoanRequest actualLoanRequest = loanRequestService.getLoanRequest(1L);

        // then
        assertEquals(loanRequest, actualLoanRequest);
    }
}