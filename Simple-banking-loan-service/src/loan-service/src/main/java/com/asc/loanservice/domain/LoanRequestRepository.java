package com.asc.loanservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
}
