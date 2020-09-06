package com.asc.loanservice.domain;

import com.asc.loanservice.domain.LoanRequest.LoanRequestStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class LoanRequestValidatorTest {

    public static final double CREDIT_INTERESTS_RATE_YEARLY = 0.04d;

    @Mock
    CustomerCheck customerCheck;
    LoanRequestValidator loanRequestValidator;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateWhenLoanValidRequestApproved() {
        //given
        loanRequestValidator = new LoanRequestValidator(customerCheck);
        int numberOfInstallments = 12;
        LoanRequest loanRequest = LoanRequest
                .builder()
                .numberOfInstallments(numberOfInstallments)
                .firstInstallmentDate(LocalDate.of(2020, 8, 1))
                .customerBirthday(LocalDate.of(2000, 5, 1))
                .numberOfInstallments(numberOfInstallments)
                .loanAmount(BigDecimal.valueOf(10000))
                .customerMonthlyIncome(BigDecimal.valueOf(5679.06))
                .customerTaxId("99999")
                .build();

        when(customerCheck.isCustomerDebtor("99999"))
                .thenReturn(false);

        //when
        LoanRequestStatus loanRequestStatus = loanRequestValidator
                .validate(loanRequest, CREDIT_INTERESTS_RATE_YEARLY);
        //then
        assertEquals(LoanRequestStatus.APPROVED, loanRequestStatus);
    }

    @Test
    public void validateWhenOneConditionNotValidLoanRequestRejected() {
        //given
        loanRequestValidator = new LoanRequestValidator(customerCheck);
        int numberOfInstallments = 12;
        LoanRequest loanRequest = LoanRequest
                .builder()
                .numberOfInstallments(numberOfInstallments)
                .firstInstallmentDate(LocalDate.of(2020, 8, 1))
                .customerBirthday(LocalDate.of(2000, 5, 1))
                .numberOfInstallments(numberOfInstallments)
                .loanAmount(BigDecimal.valueOf(
                        100000)) // monthlyRatesMaximumPercentageToIncome is bigger than 15% - result calculated on website calculator
                .customerMonthlyIncome(BigDecimal.valueOf(5679.06))
                .customerTaxId("99999")
                .build();
        when(customerCheck.isCustomerDebtor("99999"))
                .thenReturn(false);
        //when
        LoanRequestStatus loanRequestStatus = loanRequestValidator
                .validate(loanRequest, CREDIT_INTERESTS_RATE_YEARLY);
        //then
        assertEquals(LoanRequestStatus.REJECTED, loanRequestStatus);
    }
}