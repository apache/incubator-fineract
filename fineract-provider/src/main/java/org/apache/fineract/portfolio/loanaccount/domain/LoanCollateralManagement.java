package org.apache.fineract.portfolio.loanaccount.domain;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import java.math.BigDecimal;

@Entity
@Table(name = "m_loan_collateral_management")
public class LoanCollateralManagement extends AbstractPersistableCustom {

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(name = "total", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "total_collateral", nullable = false)
    private BigDecimal totalCollateralValue;

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
        this.totalValue = totalValue;
        this.totalCollateralValue = totalCollateralValue;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public BigDecimal getTotalValue() {
        return this.totalValue;
    }

    public BigDecimal getTotalCollateralValue() { return this.totalCollateralValue; }

    public Loan getLoanData() {
        return this.loan;
    }

    public CollateralManagementData getCollateral() {
        return this.collateral;
    }

}
