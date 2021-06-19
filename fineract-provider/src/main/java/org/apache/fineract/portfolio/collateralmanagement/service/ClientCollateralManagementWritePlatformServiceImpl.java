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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.collateralmanagement.domain.ClientCollateralManagement;
import org.apache.fineract.portfolio.collateralmanagement.domain.ClientCollateralManagementRepositoryWrapper;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementDomain;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientCollateralManagementWritePlatformServiceImpl implements ClientCollateralManagementWritePlatformService {

    private final ClientCollateralManagementRepositoryWrapper clientCollateralManagementRepositoryWrapper;
    private final CollateralManagementRepositoryWrapper collateralManagementRepositoryWrapper;
    private final ClientRepositoryWrapper clientRepositoryWrapper;

    @Autowired
    public ClientCollateralManagementWritePlatformServiceImpl(
            final ClientCollateralManagementRepositoryWrapper clientCollateralManagementRepositoryWrapper,
            final CollateralManagementRepositoryWrapper collateralManagementRepositoryWrapper,
            final ClientRepositoryWrapper clientRepositoryWrapper) {
        this.clientCollateralManagementRepositoryWrapper = clientCollateralManagementRepositoryWrapper;
        this.collateralManagementRepositoryWrapper = collateralManagementRepositoryWrapper;
        this.clientRepositoryWrapper = clientRepositoryWrapper;
    }

    @Transactional
    @Override
    public CommandProcessingResult addClientCollateralProduct(final JsonCommand command) {

        validateClientCollateralData(command);

        /**
         * TODO: Create client collaterals one by one or all in one
         */

        Long collateralId = command.longValueOfParameterNamed("collateralId");
        BigDecimal quantity = command.bigDecimalValueOfParameterNamed("quantity");

        final Client client = this.clientRepositoryWrapper.findOneWithNotFoundDetection(command.getClientId(), false);

        final CollateralManagementDomain collateralManagementData = this.collateralManagementRepositoryWrapper.getCollateral(collateralId);
        final ClientCollateralManagement clientCollateralManagement = ClientCollateralManagement.createNew(quantity, client,
                collateralManagementData);
        this.clientCollateralManagementRepositoryWrapper.save(clientCollateralManagement);
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withClientId(command.getClientId()).build();
    }

    private void validateClientCollateralData(final JsonCommand command) {
        String errorCode = "parameter.";
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("client-collateral");

        if (!command.parameterExists("collateralId")) {
            errorCode += "collateralId.not.exists";
            baseDataValidator.reset().parameter("collateralId").failWithCode(errorCode);
        }

        if (!command.parameterExists("quantity")) {
            errorCode += ".quantity.not.exists";
            baseDataValidator.reset().parameter("quantity").failWithCode(errorCode);
        } else {
            BigDecimal quantity = command.bigDecimalValueOfParameterNamed("quantity");
            baseDataValidator.reset().parameter("quantity").value(quantity).notNull().positiveAmount();
        }

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
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
