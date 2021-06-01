package org.apache.fineract.portfolio.collateralmanagement.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollateralManagementRepositoryWrapper {

    private final CollateralManagementDomainRepository collateralManagementDomainRepository;

    @Autowired
    public CollateralManagementRepositoryWrapper(final CollateralManagementDomainRepository collateralManagementDomainRepository) {
        this.collateralManagementDomainRepository = collateralManagementDomainRepository;
    }

    public CollateralManagementDomain create(CollateralManagementDomain collateralData) {
        return this.collateralManagementDomainRepository.save(collateralData);
    }
}
