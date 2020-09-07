package com.asc.loanservice.contracts.loanrequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
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

@Getter
@AllArgsConstructor
@Builder
public final class LoanRequestDto {
    @NotNull
    @NotEmpty
    private final String customerName;

    @NotNull
    @Past
    private final LocalDate customerBirthday;

    @NotNull
    @NotEmpty
    private final String customerTaxId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private final BigDecimal customerMonthlyIncome;

    @DecimalMin(value = "0.0", inclusive = false)
    private final BigDecimal loanAmount;

    @Min(1)
    private final Integer numberOfInstallments;

    @Future
    private final LocalDate firstInstallmentDate;
}
