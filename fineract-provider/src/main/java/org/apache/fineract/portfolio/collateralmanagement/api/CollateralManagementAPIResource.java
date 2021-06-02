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
package org.apache.fineract.portfolio.collateralmanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateralmanagement.service.CollateralManagementReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementData;

import java.util.List;

@Path("/collateral-management")
@Component
@Scope("singleton")
@Tag(name = "Collateral Management", description = "Collateral Management is for managing collateral operations")
public class CollateralManagementAPIResource {

    private final DefaultToApiJsonSerializer<CollateralManagementData> apiJsonSerializerService;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final PlatformSecurityContext context;
    private final CodeValueReadPlatformService codeValueReadPlatformService;
    private final CollateralManagementReadPlatformService collateralManagementReadPlatformService;
    private final String collateralReadPermission = "COLLATERAL_PRODUCT";

    @Autowired
    public CollateralManagementAPIResource(final DefaultToApiJsonSerializer<CollateralManagementData> apiJsonSerializerService,
            final ApiRequestParameterHelper apiRequestParameterHelper,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService, final PlatformSecurityContext context,
            final CodeValueReadPlatformService codeValueReadPlatformService, final CollateralManagementReadPlatformService collateralManagementReadPlatformService) {
        this.apiJsonSerializerService = apiJsonSerializerService;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        this.context = context;
        this.codeValueReadPlatformService = codeValueReadPlatformService;
        this.collateralManagementReadPlatformService = collateralManagementReadPlatformService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Create a new collateral", description = "Collateral Creation")
    public String createCollateral(@Parameter(hidden = true) final String apiRequestBodyAsJson) {
        final CommandWrapper commandWrapper = new CommandWrapperBuilder().createCollateral().withJson(apiRequestBodyAsJson).build();
        final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService.logCommandSource(commandWrapper);
        return this.apiJsonSerializerService.serialize(commandProcessingResult);
    }

    @GET
    @Path("id")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Get Collateral", description = "Fetch Collateral")
    public String getCollateral(@PathParam("id") @Parameter(description = "collateral_id") final Long collateralId,
                                @Context final UriInfo uriInfo) {

        this.context.authenticatedUser().validateHasReadPermission(this.collateralReadPermission);

        final CollateralManagementData collateralManagementData = this.collateralManagementReadPlatformService.getCollateralProduct(collateralId);

        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());

        return this.apiJsonSerializerService.serialize(collateralManagementData);
    }

    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Get All Collaterals", description = "Fetch all Collateral Products")
    public String getAllCollaterals(@Context final UriInfo uriInfo) {

        this.context.authenticatedUser().validateHasReadPermission(this.collateralReadPermission);
        List<CollateralManagementData> collateralManagementDataList = this.collateralManagementReadPlatformService.getAllCollateralProducts();
        return this.apiJsonSerializerService.serialize(collateralManagementDataList);

    }


}
