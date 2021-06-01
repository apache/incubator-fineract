/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.portfolio.collateralmanagement.domain;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "m_collateral")
public class CollateralManagementDomain extends AbstractPersistableCustom {

    @Column(name = "quality", nullable = false, length = 20)
    private String quality;

    @Column(name = "base_price", nullable = false)
    private Double basePrice;

    @Column(name = "unit_type", nullable = false, length = 10)
    private String unityType;

    @Column(name = "pct_to_base", nullable = false, length = 3)
    private Double pctToBase;

    @Column(name = "currency", nullable = false, columnDefinition = "USD", length = 10)
    private String currency;

    @OneToMany(mappedBy = "m_collateral", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<LoanCollateralManagement> loanCollateralManagements;

    public CollateralManagementDomain() {

    }

    public CollateralManagementDomain(final String quality, final Double basePrice, final String unityType, final Double pctToBase,
            final String currency, final Set<LoanCollateralManagement> loanCollateralManagements) {
        this.basePrice = basePrice;
        this.currency = currency;
        this.pctToBase = pctToBase;
        this.unityType = unityType;
        this.quality = quality;
        this.loanCollateralManagements = loanCollateralManagements;
    }

    public String getQuality() {
        return this.quality;
    }

    public String getUnityType() {
        return this.unityType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Double getBasePrice() {
        return this.basePrice;
    }

    public Double getPctToBase() {
        return this.pctToBase;
    }

    public Set<LoanCollateralManagement> getLoanCollateralManagements() {
        return this.loanCollateralManagements;
    }

}
