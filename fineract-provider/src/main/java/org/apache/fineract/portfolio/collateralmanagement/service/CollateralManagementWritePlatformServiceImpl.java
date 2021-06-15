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

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.organisation.monetary.domain.ApplicationCurrency;
import org.apache.fineract.organisation.monetary.domain.ApplicationCurrencyRepository;
import org.apache.fineract.portfolio.collateralmanagement.api.CollateralAPIConstants;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementData;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollateralManagementWritePlatformServiceImpl implements CollateralManagementWritePlatformService {

    private final CollateralManagementRepositoryWrapper collateralManagementRepositoryWrapper;
    private final ApplicationCurrencyRepository applicationCurrencyRepository;

    @Autowired
    public CollateralManagementWritePlatformServiceImpl(final CollateralManagementRepositoryWrapper collateralManagementRepositoryWrapper,
            final ApplicationCurrencyRepository applicationCurrencyRepository) {
        this.collateralManagementRepositoryWrapper = collateralManagementRepositoryWrapper;
        this.applicationCurrencyRepository = applicationCurrencyRepository;
    }

    @Transactional
    @Override
    public CommandProcessingResult createCollateral(final JsonCommand jsonCommand) {
        /**
         * TODO: Authenticate User
         */
        final String currencyParamName = jsonCommand
                .stringValueOfParameterNamed(CollateralAPIConstants.CollateralJSONinputParams.CURRENCY.getValue());
        final ApplicationCurrency applicationCurrency = this.applicationCurrencyRepository.findOneByCode(currencyParamName);
        final CollateralManagementData collateral = CollateralManagementData.createNew(jsonCommand, applicationCurrency);
        this.collateralManagementRepositoryWrapper.create(collateral);
        return new CommandProcessingResultBuilder().withCommandId(jsonCommand.commandId()).withEntityId(collateral.getId()).build();
    }

    @Transactional
    @Override
    public CommandProcessingResult updateCollateral(final Long collateralId, JsonCommand jsonCommand) {
        final CollateralManagementData collateral = this.collateralManagementRepositoryWrapper.getCollateral(collateralId);
        final String currencyParamName = CollateralAPIConstants.CollateralJSONinputParams.CURRENCY.getValue();
        final ApplicationCurrency applicationCurrency = this.applicationCurrencyRepository
                .findOneByCode(jsonCommand.stringValueOfParameterNamed(currencyParamName));
        if (jsonCommand.isChangeInStringParameterNamed(currencyParamName, applicationCurrency.getCode())) {
            final String newValue = jsonCommand.stringValueOfParameterNamed(currencyParamName);
            applicationCurrency.setCode(newValue);
        }
        collateral.update(jsonCommand, applicationCurrency);
        this.collateralManagementRepositoryWrapper.update(collateral);
        return new CommandProcessingResultBuilder().withCommandId(jsonCommand.commandId()).withEntityId(jsonCommand.entityId()).build();
    }

    @Transactional
    @Override
    public CommandProcessingResult deleteCollateral(final Long collateralId) {
        this.collateralManagementRepositoryWrapper.delete(collateralId);
        return new CommandProcessingResultBuilder().withEntityId(collateralId).build();
    }
}
