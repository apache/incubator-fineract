package org.apache.fineract.portfolio.loanaccount.data;

import java.math.BigDecimal;

public class LoanCollateralManagementData {

    private final Long clientId;

    private final BigDecimal quantity;

    private final BigDecimal total;

    private final BigDecimal totalCollateral;

    public LoanCollateralManagementData (final Long clientId, final BigDecimal quantity, final BigDecimal total, final BigDecimal totalCollateral) {
        this.clientId = clientId;
        this.quantity = quantity;
        this.totalCollateral = totalCollateral;
        this.total = total;
    }

    public Long getClientId() { return this.clientId; }

    public BigDecimal getQuantity() { return this.quantity; }

    public BigDecimal getTotal() { return this.total; }

    public BigDecimal getTotalCollateral() { return this.totalCollateral; }
}
