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

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.client.domain.Client;

@Entity
@Table(name = "m_client_collateral_management")
public class ClientCollateralManagement extends AbstractPersistableCustom {

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "total_collateral", nullable = false)
    private Double totalCollateral;

    public ClientCollateralManagement() {

    }

    public ClientCollateralManagement(final Double quantity, final Double totalCollateral, final Client client) {
        this.client = client;
        this.totalCollateral = totalCollateral;
        this.quantity = quantity;
    }

    public ClientCollateralManagement(final Double quantity, final Double totalCollateral) {
        this.totalCollateral = totalCollateral;
        this.quantity = quantity;
    }

    public ClientCollateralManagement createNew(JsonCommand jsonCommand) {
        Double total = jsonCommand.bigDecimalValueOfParameterNamed("totalCollateral").doubleValue();
        Double quantity = jsonCommand.bigDecimalValueOfParameterNamed("quantity").doubleValue();
        return new ClientCollateralManagement(quantity, total);
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public Double getTotalCollateral() {
        return this.totalCollateral;
    }

    public Client getClient() {
        return this.client;
    }

}
