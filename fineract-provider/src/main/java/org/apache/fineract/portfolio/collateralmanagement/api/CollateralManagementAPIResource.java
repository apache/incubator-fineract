package org.apache.fineract.portfolio.collateralmanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateralmanagement.data.CollateralData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/collateral-management")
@Component
@Scope("singleton")
@Tag(name = "Collateral Management", description = "Collateral Management is for managing collateral operations")
public class CollateralManagementAPIResource {

    private final DefaultToApiJsonSerializer<CollateralData> apiJsonSerializerService;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final PlatformSecurityContext context;
    private final CodeValueReadPlatformService codeValueReadPlatformService;

    @Autowired
    public CollateralManagementAPIResource (final DefaultToApiJsonSerializer<CollateralData> apiJsonSerializerService,
                                            final ApiRequestParameterHelper apiRequestParameterHelper, final  PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
                                            final PlatformSecurityContext context, final CodeValueReadPlatformService codeValueReadPlatformService) {
        this.apiJsonSerializerService = apiJsonSerializerService;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        this.context = context;
        this.codeValueReadPlatformService = codeValueReadPlatformService;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON })
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Create a new collateral", description = "Collateral Creation")
    public String createCollateral(@Parameter(hidden = true) final String apiRequestBodyAsJson) {
        /**
         * TODO: Call command handlers and create a new collateral
         */
        return null;
    }

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllCollaterals(@Context UriInfo uriInfo) {
        /**
         * TODO: Call command handlers and create a new collateral
         */
        return null;
    }

    @GET
    @Path("{collateralId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String getCollateralData(@Context UriInfo uriInfo, @PathParam("collateralId") @Parameter(description = "collaterald") final Long collateralId) {
        /**
         * TODO: Implement command handlers for retrieving data
         */
        return null;
    }

}
