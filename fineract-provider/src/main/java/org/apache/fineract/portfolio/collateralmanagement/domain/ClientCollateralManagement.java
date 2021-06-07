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

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.collateralmanagement.api.CollateralAPIConstants;

@Entity
@Table(name = "m_client_collateral_management")
public class ClientCollateralManagement extends AbstractPersistableCustom {

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "total_collateral", nullable = false)
    private BigDecimal totalCollateral;

    public ClientCollateralManagement() {

    }

    public ClientCollateralManagement(final BigDecimal quantity, final BigDecimal totalCollateral, final Client client, final BigDecimal total) {
        this.client = client;
        this.totalCollateral = totalCollateral;
        this.quantity = quantity;
        this.total = total;
    }

    public ClientCollateralManagement(final BigDecimal quantity, final BigDecimal totalCollateral) {
        this.totalCollateral = totalCollateral;
        this.quantity = quantity;
    }

    public ClientCollateralManagement createNew(JsonCommand jsonCommand) {
        BigDecimal total = jsonCommand.bigDecimalValueOfParameterNamed("totalCollateral");
        BigDecimal quantity = jsonCommand.bigDecimalValueOfParameterNamed("quantity");
        return new ClientCollateralManagement(quantity, total);
    }

    public void update(JsonCommand command) {
        final String quantity = CollateralAPIConstants.CollateralJSONinputParams.QUANTITY.getValue();
        if (command.isChangeInBigDecimalParameterNamed(quantity, this.quantity)) {
            final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(quantity);
            this.quantity = newValue;
        }

        final String totalCollateralValue = CollateralAPIConstants.CollateralJSONinputParams.TOTAL_COLLATERAL_VALUE.getValue();
        if (command.isChangeInBigDecimalParameterNamed(totalCollateralValue, this.totalCollateral)) {
            final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(quantity);
            this.totalCollateral = newValue;
        }

    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public BigDecimal getTotalCollateral() {
        return this.totalCollateral;
    }

    public Client getClient() {
        return this.client;
    }

}
