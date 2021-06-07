package org.apache.fineract.portfolio.loanaccount.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoanCollateralManagementRepository extends JpaRepository<LoanCollateralManagement, Long>, JpaSpecificationExecutor<LoanCollateralManagement> {
}
