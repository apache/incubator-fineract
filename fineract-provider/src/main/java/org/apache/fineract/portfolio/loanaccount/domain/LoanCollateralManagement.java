package org.apache.fineract.portfolio.loanaccount.domain;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.collateralmanagement.domain.ClientCollateralManagement;
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

//    @Column(name = "total", nullable = false)
//    private BigDecimal totalValue;
//
//    @Column(name = "total_collateral", nullable = false)
//    private BigDecimal totalCollateralValue;

    @Column(name = "client_collateral_id", nullable = false)
    private ClientCollateralManagement clientCollateralManagement;


    public LoanCollateralManagement() {

    }

    public LoanCollateralManagement(final BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void setClientCollateralManagement(ClientCollateralManagement clientCollateralManagement) {
        this.clientCollateralManagement = clientCollateralManagement;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public Loan getLoanData() {
        return this.loan;
    }

    public ClientCollateralManagement getClientCollateralManagement() { return this.clientCollateralManagement; }

}
