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

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain.PostDatedChecksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RepaymentWithPostDatedChecksWritePlatformServiceImpl implements RepaymentWithPostDatedChecksWritePlatformService{

    private final PostDatedChecksRepository postDatedChecksRepository;
    @Autowired
    public RepaymentWithPostDatedChecksWritePlatformServiceImpl(final PostDatedChecksRepository postDatedChecksRepository) {
        this.postDatedChecksRepository = postDatedChecksRepository;
    }

    @Transactional
    @Override
    public CommandProcessingResult addPostDatedChecks(JsonCommand command) {
        return CommandProcessingResult.empty();
    }

    @Transactional
    @Override
    public CommandProcessingResult deletePostDatedChecks(final JsonCommand command) {
        this.postDatedChecksRepository.deleteById(command.entityId());
        return new CommandProcessingResultBuilder()
                .withCommandId(command.commandId())
                .withLoanId(command.getLoanId())
                .withEntityId(command.entityId())
                .build();
    }
}
