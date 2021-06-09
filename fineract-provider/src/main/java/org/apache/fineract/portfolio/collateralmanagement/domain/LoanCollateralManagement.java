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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;

import java.math.BigDecimal;

@Entity
@Table(name = "m_loan_collateral_management")
public class LoanCollateralManagement extends AbstractPersistableCustom {

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

//    @Column(name = "total", nullable = false)
//    private BigDecimal totalValue;
//
//    @Column(name = "total_collateral", nullable = false)
//    private BigDecimal totalCollateralValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "collateral_id", nullable = false)
    private CollateralManagementData collateral;

    public LoanCollateralManagement() {

    }

    public LoanCollateralManagement(final BigDecimal quantity, final BigDecimal totalValue, final Loan loan,
            final CollateralManagementData collateral, final BigDecimal totalCollateralValue) {
        this.collateral = collateral;
        this.loan = loan;
        this.quantity = quantity;
//        this.totalValue = totalValue;
//        this.totalCollateralValue = totalCollateralValue;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

//    public BigDecimal getTotalValue() {
//        return this.totalValue;
//    }
//
//    public BigDecimal getTotalCollateralValue() { return this.totalCollateralValue; }

    public Loan getLoanData() {
        return this.loan;
    }

    public CollateralManagementData getCollateral() {
        return this.collateral;
    }
}
