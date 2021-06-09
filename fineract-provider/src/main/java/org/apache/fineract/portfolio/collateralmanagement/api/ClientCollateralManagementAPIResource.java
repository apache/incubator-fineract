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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateralmanagement.domain.ClientCollateralManagement;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementData;
import org.apache.fineract.portfolio.collateralmanagement.service.ClientCollateralManagementReadPlatformService;
import org.apache.fineract.portfolio.collateralmanagement.service.CollateralManagementReadPlatformService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/clients/{clientId}/collaterals")
@Component
@Scope("singleton")
@Tag(name = "Client Collateral Management", description = "Client Collateral Management is for managing collateral operations")
public class ClientCollateralManagementAPIResource {

    private final DefaultToApiJsonSerializer<ClientCollateralManagement> apiJsonSerializerService;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final PlatformSecurityContext context;
    private final CodeValueReadPlatformService codeValueReadPlatformService;
    private final ClientCollateralManagementReadPlatformService clientCollateralManagementReadPlatformService;

    public ClientCollateralManagementAPIResource(final DefaultToApiJsonSerializer<ClientCollateralManagement> apiJsonSerializerService,
                                                 final ApiRequestParameterHelper apiRequestParameterHelper,
                                                 final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
                                                 final PlatformSecurityContext context, final CodeValueReadPlatformService codeValueReadPlatformService,
                                                 final ClientCollateralManagementReadPlatformService clientCollateralManagementReadPlatformService) {
        this.apiJsonSerializerService = apiJsonSerializerService;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        this.context = context;
        this.codeValueReadPlatformService = codeValueReadPlatformService;
        this.clientCollateralManagementReadPlatformService = clientCollateralManagementReadPlatformService;
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Get Clients Collateral Products", description = "Get Collateral Product of a Client")
    public String getClientCollateral(@PathParam("clientId") @Parameter(description = "clientId") final Long clientId) {

        this.context.authenticatedUser()
                .validateHasReadPermission(CollateralAPIConstants.CollateralJSONinputParams.CLIENT_COLLATERAL_PRODUCT_READ_PERMISSION.getValue());

        List<ClientCollateralManagement> collateralProductList = this.clientCollateralManagementReadPlatformService
                .getCollateralProductsPerClient(clientId);

        return this.apiJsonSerializerService.serialize(collateralProductList);
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Add New Collateral For a Client", description = "Add New Collateral For a Client")
    public String addCollateral(@PathParam("clientId") @Parameter(description = "clientId") final Long clientId,
                                @Parameter(hidden = true) String apiJsonRequestBody) {
        final CommandWrapper commandWrapper = new CommandWrapperBuilder().addClientCollateralProduct(clientId).withJson(apiJsonRequestBody)
                .build();

        final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService.logCommandSource(commandWrapper);

        return this.apiJsonSerializerService.serialize(commandProcessingResult);
    }

    @PUT
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Update New Collateral of a Client", description = "Update New Collateral of a Client")
    public String updateCollateral(@PathParam("clientId") @Parameter(description = "clientId") final Long clientId,
                                   @QueryParam("collateralId") @Parameter(description = "collateralId") final Long collateralId,
                                   @Parameter(hidden = true) String apiJsonRequestBody) {

        final CommandWrapper commandWrapper = new CommandWrapperBuilder().updateClientCollateralProduct(clientId, collateralId)
                .withJson(apiJsonRequestBody).build();

        final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService.logCommandSource(commandWrapper);

        return this.apiJsonSerializerService.serialize(commandProcessingResult);
    }

//    @DELETE
//    @Path("{collateralId}")
//    @Produces({ MediaType.APPLICATION_JSON })
//    @Consumes({ MediaType.APPLICATION_JSON })
//    @Operation(summary = "Delete Client Collateral", description = "Delete Client Collateral")
//    public String deleteCollateral(@PathParam("clientId") @Parameter(description = "clientId") final Long clientId,
//                                   @PathParam("collateralId") @Parameter(description = "collateralId") final Long collateralId) {
//        final CommandWrapper commandWrapper = new CommandWrapperBuilder().deleteClientCollateralProduct(collateralId, clientId).build();
//
//        final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService.logCommandSource(commandWrapper);
//
//        return this.apiJsonSerializerService.serialize(commandProcessingResult);
//
//    }

}
