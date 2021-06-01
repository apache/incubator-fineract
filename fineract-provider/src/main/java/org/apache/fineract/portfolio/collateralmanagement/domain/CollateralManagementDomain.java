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

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "m_collateral_management")
public class CollateralManagementDomain extends AbstractPersistableCustom {

    @Column(name = "quality", nullable = false, length = 20)
    private String quality;

    @Column(name = "base_price", nullable = false)
    private Double basePrice;

    @Column(name = "unit_type", nullable = false, length = 10)
    private String unitType;

    @Column(name = "pct_to_base", nullable = false, length = 3)
    private Double pctToBase;

    @Column(name = "currency", nullable = false, columnDefinition = "USD", length = 10)
    private String currency;

    @OneToMany(mappedBy = "collateral", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<LoanCollateralManagement> loanCollateralManagements = new HashSet<>();

    public CollateralManagementDomain() {

    }

    private CollateralManagementDomain(final String quality, final Double basePrice, final String unitType, final Double pctToBase,
            final String currency) {
        this.basePrice = basePrice;
        this.currency = currency;
        this.pctToBase = pctToBase;
        this.unitType = unitType;
        this.quality = quality;
    }

    public static CollateralManagementDomain createNew(JsonCommand jsonCommand) {
        String quality = jsonCommand.stringValueOfParameterNamed("quality");
        Double basePrice = jsonCommand.bigDecimalValueOfParameterNamed("basePrice").doubleValue();
        Double pctToBase = jsonCommand.bigDecimalValueOfParameterNamedDefaultToNullIfZero("pctToBase").doubleValue();
        String unitType = jsonCommand.stringValueOfParameterNamed("unityType");
        String currency = jsonCommand.stringValueOfParameterNamed("currency");
        return new CollateralManagementDomain(quality, basePrice, unitType,pctToBase, currency);
    }

    public String getQuality() {
        return this.quality;
    }

    public String getUnitType() {
        return this.unitType;
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

    // public Set<LoanCollateralManagement> getLoanCollateralManagements() {
    // return this.loanCollateralManagements;
    // }

}
