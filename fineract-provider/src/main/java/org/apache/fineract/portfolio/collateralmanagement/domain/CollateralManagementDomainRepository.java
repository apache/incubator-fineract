package org.apache.fineract.portfolio.collateralmanagement.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CollateralManagementDomainRepository extends JpaRepository<CollateralManagementDomain, Long>, JpaSpecificationExecutor<CollateralManagementDomain> {


}

