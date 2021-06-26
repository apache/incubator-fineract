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
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "m_repayment_with_post_dated_checks")
public class PostDatedChecks extends AbstractPersistableCustom {

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "loan_id", referencedColumnName = "id", nullable = false)
//    private Loan loan;
    @OneToOne(optional = false)
    @JoinColumn(name = "repayment_id", referencedColumnName = "id", nullable = false)
    private LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment;
    @Column(name = "acoount_no", nullable = false, length = 10)
    private Long accountNo;
    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;
    @Column(name = "amount", scale = 6, precision = 19)
    private BigDecimal amount;

    private PostDatedChecks() {}

    private PostDatedChecks(final Long accountNo, final String bankName, final BigDecimal amount, final LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment) {
        this.bankName = bankName;
        this.accountNo = accountNo;
        this.amount = amount;
        this.loanRepaymentScheduleInstallment = loanRepaymentScheduleInstallment;
    }

    public static PostDatedChecks instanceOf(final Long accountNo, final String bankName, final BigDecimal amount, final LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment) {
        return new PostDatedChecks(accountNo, bankName, amount, loanRepaymentScheduleInstallment);
    }

//    public void setLoan(Loan loan) {
//        this.loan = loan;
//    }

    public void setLoanRepaymentScheduleInstallment(final LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment) {
        this.loanRepaymentScheduleInstallment = loanRepaymentScheduleInstallment;
    }

//    public Loan getLoan() {
//        return this.loan;
//    }

    public LoanRepaymentScheduleInstallment getLoanRepaymentScheduleInstallment() {
        return this.loanRepaymentScheduleInstallment;
    }

    public String getBankName() {
        return this.bankName;
    }

    public Long getAccountNo() {
        return this.accountNo;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
}
