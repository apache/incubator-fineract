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
package org.apache.fineract.portfolio.collateralmanagement.domain;

import java.util.List;
import org.apache.fineract.portfolio.collateralmanagement.exception.ClientCollateralNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientCollateralManagementRepositoryWrapper {

    private final ClientCollateralManagementRepository clientCollateralManagementRepository;

    @Autowired
    public ClientCollateralManagementRepositoryWrapper(final ClientCollateralManagementRepository clientCollateralManagementRepository) {
        this.clientCollateralManagementRepository = clientCollateralManagementRepository;
    }

    public List<ClientCollateralManagement> getCollateralsPerClient(final Long clientId) {
        return this.clientCollateralManagementRepository.findByClientId(clientId);
    }

    public ClientCollateralManagement getCollateral(final Long collateralId) {
        return this.clientCollateralManagementRepository.findById(collateralId)
                .orElseThrow(() -> new ClientCollateralNotFoundException(collateralId));
    }

    public ClientCollateralManagement updateClientCollateralProduct(final ClientCollateralManagement clientCollateralManagement) {
        return this.clientCollateralManagementRepository.saveAndFlush(clientCollateralManagement);
    }

    public void deleteClientCollateralProduct(final Long collateralId) {
        this.clientCollateralManagementRepository.deleteById(collateralId);
    }

    public void save(ClientCollateralManagement clientCollateralManagement) {
        this.clientCollateralManagementRepository.save(clientCollateralManagement);
    }

    public void saveAndFlush(ClientCollateralManagement clientCollateralManagement) {
        this.clientCollateralManagementRepository.saveAndFlush(clientCollateralManagement);
    }
}
