package org.apache.fineract.portfolio.collateralmanagement.handler;

import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.collateralmanagement.service.CollateralManagementWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@CommandType(entity = "COLLATERAL_PRODUCT", action = "DELETE")
public class DeleteCollateralProductHandler implements NewCommandSourceHandler {

    private final CollateralManagementWritePlatformService collateralManagementWritePlatformService;

    @Autowired
    public DeleteCollateralProductHandler(final CollateralManagementWritePlatformService collateralManagementWritePlatformService) {
        this.collateralManagementWritePlatformService = collateralManagementWritePlatformService;
    }

    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.collateralManagementWritePlatformService.deleteCollateral(command.entityId());
    }
}
