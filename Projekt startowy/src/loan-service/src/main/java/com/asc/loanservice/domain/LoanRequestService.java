package com.asc.loanservice.domain;

import com.asc.loanservice.domain.LoanRequest.LoanRequestStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;
    private final LoanRequestValidator loanRequestValidator;

    public LoanRequestService(
            LoanRequestRepository loanRequestRepository,
            LoanRequestValidator loanRequestValidator) {
        this.loanRequestRepository = loanRequestRepository;
        this.loanRequestValidator = loanRequestValidator;
    }

    public LoanRequest getLoanRequest(long loanRequestId) {
        Optional<LoanRequest> loanRequestResult = loanRequestRepository
                .findById(loanRequestId);
        return loanRequestResult.orElse(null);
    }

    public LoanRequest registerLoanRequest(LoanRequest loanRequest) {
        LoanRequestStatus loanRequestStatus = loanRequestValidator.validate(loanRequest);
        loanRequest.setEvaluationResult(loanRequestStatus);
        return loanRequestRepository.save(loanRequest);
    }
}
