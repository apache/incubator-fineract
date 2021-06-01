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
package org.apache.fineract.portfolio.collateralmanagement.data;

public class CollateralManagementData {

    private final String quality;

    private final Double basePrice;

    private final String unityType;

    private final Double pctToBase;

    private final String currency;

    public CollateralManagementData(final String quality, final Double basePrice, final String unityType, final Double pctToBase,
            final String currency) {
        this.basePrice = basePrice;
        this.pctToBase = pctToBase;
        this.quality = quality;
        this.unityType = unityType;
        this.currency = currency;
    }

}
