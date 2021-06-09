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
package org.apache.fineract.portfolio.collateralmanagement.service;

import javax.transaction.Transactional;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.portfolio.collateralmanagement.domain.ClientCollateralManagement;
import org.apache.fineract.portfolio.collateralmanagement.domain.ClientCollateralManagementRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientCollateralManagementWritePlatformServiceImpl implements ClientCollateralManagementWritePlatformService {

    private final ClientCollateralManagementRepositoryWrapper clientCollateralManagementRepositoryWrapper;

    @Autowired
    public ClientCollateralManagementWritePlatformServiceImpl(
            final ClientCollateralManagementRepositoryWrapper clientCollateralManagementRepositoryWrapper) {
        this.clientCollateralManagementRepositoryWrapper = clientCollateralManagementRepositoryWrapper;
    }

    @Transactional
    @Override
    public CommandProcessingResult addClientCollateralProduct(final JsonCommand command) {
        return CommandProcessingResult.empty();
    }

    @Transactional
    @Override
    public CommandProcessingResult updateClientCollateralProduct(final JsonCommand command) {
        final ClientCollateralManagement collateral = this.clientCollateralManagementRepositoryWrapper.getCollateral(command.entityId());
        collateral.update(command);
        this.clientCollateralManagementRepositoryWrapper.updateClientCollateralProduct(collateral);
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(command.entityId())
                .withClientId(command.getClientId()).build();
    }

    @Transactional
    @Override
    public CommandProcessingResult deleteClientCollateralProduct(final Long collateralId) {
        this.clientCollateralManagementRepositoryWrapper.deleteClientCollateralProduct(collateralId);
        return new CommandProcessingResultBuilder().withEntityId(collateralId).build();
    }

}
