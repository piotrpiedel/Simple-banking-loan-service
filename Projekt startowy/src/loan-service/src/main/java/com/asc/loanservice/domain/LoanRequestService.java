package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.loanrequest.LoanRequestRegistrationResultDto;
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

    public LoanRequestRegistrationResultDto getLoanRequest(long loanRequestId) {
        Optional<LoanRequest> loanRequestResult = loanRequestRepository
                .findById(loanRequestId);
        if (loanRequestResult.isPresent()) {
            LoanRequest loanRequest = loanRequestResult.get();
            return new LoanRequestRegistrationResultDto(
                    String.valueOf(loanRequest.getLoanRequestNumber()),
                    loanRequest.getEvaluationResult());
        }
        return null;
    }

    public LoanRequest registerLoanRequest(LoanRequest loanRequest) {
        LoanRequest emptyLoanRequest = loanRequestRepository.save(new LoanRequest());
        LoanRequest.LoanRequestStatus loanRequestStatus = loanRequestValidator
                .validate(loanRequest);
        return fillLoanRequestWithdata(emptyLoanRequest, loanRequest);
    }

    private LoanRequest fillLoanRequestWithdata(
            LoanRequest emptyLoanRequest, LoanRequest loanRequest) {
        return new LoanRequest();
    }
}
