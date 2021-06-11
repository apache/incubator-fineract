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
package org.apache.fineract.portfolio.loanaccount.data;

import java.math.BigDecimal;

public class LoanCollateralManagementData {

    private final Long clientId;

    private final BigDecimal quantity;

    private final BigDecimal total;

    private final BigDecimal totalCollateral;

    private final Long loanId;

    public LoanCollateralManagementData(final Long clientId, final BigDecimal quantity, final BigDecimal total,
            final BigDecimal totalCollateral, final Long loanId) {
        this.clientId = clientId;
        this.quantity = quantity;
        this.totalCollateral = totalCollateral;
        this.total = total;
        this.loanId = loanId;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public BigDecimal getTotalCollateral() {
        return this.totalCollateral;
    }

    public Long getLoanId() { return this.loanId; }
}
