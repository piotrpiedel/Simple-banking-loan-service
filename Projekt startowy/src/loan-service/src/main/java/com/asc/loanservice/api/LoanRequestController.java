package com.asc.loanservice.api;

import com.asc.loanservice.contracts.loanrequest.LoanRequestDataDto;
import com.asc.loanservice.contracts.loanrequest.LoanRequestDto;
import com.asc.loanservice.contracts.loanrequest.LoanRequestDtoMapper;
import com.asc.loanservice.contracts.loanrequest.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.LoanRequest;
import com.asc.loanservice.domain.LoanRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;
    private final LoanRequestDtoMapper loanRequestDtoMapper;

    public LoanRequestController(
            LoanRequestService loanRequestService,
            LoanRequestDtoMapper loanRequestDtoMapper) {
        this.loanRequestService = loanRequestService;
        this.loanRequestDtoMapper = loanRequestDtoMapper;
    }

    @PostMapping
    public LoanRequestRegistrationResultDto register(
            @RequestBody @Valid LoanRequestDto loanRequestDto) {
        LoanRequest receivedLoanRequest = loanRequestDtoMapper.asEntity(loanRequestDto);
        LoanRequest registeredLoanRequest = loanRequestService
                .registerLoanRequest(receivedLoanRequest);
        return buildLoanRequestRegistrationResult(registeredLoanRequest);
    }

    private LoanRequestRegistrationResultDto buildLoanRequestRegistrationResult(
            LoanRequest registeredLoanRequest) {
        return new LoanRequestRegistrationResultDto(
                String.valueOf(registeredLoanRequest.getLoanRequestNumber()),
                registeredLoanRequest.getEvaluationResult());
    }

    @GetMapping("/{loanNumber}")
    public LoanRequestDataDto getByNumber(@PathVariable("loanNumber") String loanNumber) {
        //TODO: implement
        return null;
    }
}
