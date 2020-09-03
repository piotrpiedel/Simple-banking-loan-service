package com.asc.loanservice.contracts.loanrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDataDto {

    @NotNull
    @NotEmpty
    private String loanRequestNumber;

    @NotNull
    @NotEmpty
    private String customerName;

    @NotNull
    @Past
    private LocalDate customerBirthday;

    @NotNull
    @NotEmpty
    private String customerTaxId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal customerMonthlyIncome;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal loanAmount;

    @Min(1)
    private Integer numberOfInstallments;

    @Future
    private LocalDate firstInstallmentDate;

    private LoanRequestEvaluationResult evaluationResult;
    private LocalDateTime registrationDate;
}
