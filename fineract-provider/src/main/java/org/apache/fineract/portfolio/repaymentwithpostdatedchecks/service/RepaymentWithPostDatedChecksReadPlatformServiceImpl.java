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
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.service;

import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepository;
import org.apache.fineract.portfolio.loanaccount.exception.LoanNotFoundException;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.data.PostDatedChecksData;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain.PostDatedChecks;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain.PostDatedChecksRepository;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.exception.PostDatedCheckNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RepaymentWithPostDatedChecksReadPlatformServiceImpl implements RepaymentWithPostDatedChecksReadPlatformService {

    private final PostDatedChecksRepository postDatedChecksRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public RepaymentWithPostDatedChecksReadPlatformServiceImpl(final PostDatedChecksRepository postDatedChecksRepository,
                                                               final LoanRepository loanRepository) {
        this.postDatedChecksRepository = postDatedChecksRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public List<PostDatedChecksData> getPostDatedChecks(final Long id) {
        final Loan loan = this.loanRepository.findById(id).orElseThrow(()-> new LoanNotFoundException(id));
        final List<PostDatedChecks> postDatedChecks = loan.getPostDatedChecks();
        List<PostDatedChecksData> postDatedChecksDataList = new ArrayList<>();
        for (PostDatedChecks postDatedCheckObject: postDatedChecks) {
            /**
             * Check how date type converted into local date.
             */
            postDatedChecksDataList.add(PostDatedChecksData.from(postDatedCheckObject.getLoanRepaymentScheduleInstallment().getDueDate(), postDatedCheckObject.getId(), postDatedCheckObject.getLoanRepaymentScheduleInstallment().getInstallmentNumber(),
                    postDatedCheckObject.getAccountNo(), postDatedCheckObject.getAmount(), postDatedCheckObject.getBankName()));
        }
        return postDatedChecksDataList;
    }

    @Override
    public PostDatedChecksData getPostDatedCheck(final Long id) {
        final PostDatedChecks postDatedChecks = this.postDatedChecksRepository.findById(id).orElseThrow(() -> new PostDatedCheckNotFoundException(id));
        return PostDatedChecksData.from(postDatedChecks.getLoanRepaymentScheduleInstallment().getDueDate(), postDatedChecks.getId(),
                postDatedChecks.getLoanRepaymentScheduleInstallment().getInstallmentNumber(),
                postDatedChecks.getAccountNo(), postDatedChecks.getAmount(), postDatedChecks.getBankName());
    }
}
