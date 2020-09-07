package com.asc.loanservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long loanRequestNumber;
    private String customerName;
    private LocalDate customerBirthday;
    private String customerTaxId;
    private BigDecimal customerMonthlyIncome;
    private BigDecimal loanAmount;
    private Integer numberOfInstallments;
    private LocalDate firstInstallmentDate;
    @Enumerated(EnumType.STRING)
    private LoanRequestStatus evaluationResult;
    private LocalDateTime registrationDate;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }

    public enum LoanRequestStatus {
        APPROVED, REJECTED
    }

    public LoanRequest addEvaluationResultToLoanRequest(LoanRequestStatus evaluationResult) {
        return this.toBuilder().evaluationResult(evaluationResult).build();
    }
}
