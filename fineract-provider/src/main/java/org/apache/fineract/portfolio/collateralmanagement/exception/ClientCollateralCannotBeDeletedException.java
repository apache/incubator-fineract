package org.apache.fineract.portfolio.collateralmanagement.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class ClientCollateralCannotBeDeletedException extends AbstractPlatformDomainRuleException {

    /*** enum of reasons of why Collateral cannot be waived **/
    public enum ClientCollateralCannotBeDeletedReason {

        CLIENT_COLLATERAL_IS_ALREADY_ATTACHED;

        public String errorMessage() {
            if (name().toString().equalsIgnoreCase("CLIENT_COLLATERAL_IS_ALREADY_ATTACHED")) {
                return "This client collateral cannot be deleted as this is associated with one or more loan collaterals";
            }
            return name().toString();
        }

        public String errorCode() {
            if (name().toString().equalsIgnoreCase("CLIENT_COLLATERAL_IS_ALREADY_ATTACHED")) {
                return "error.msg.client.collateral.is.already.associated.with.loan.collateral";
            }
            return name().toString();
        }
    }

    public ClientCollateralCannotBeDeletedException(final ClientCollateralCannotBeDeletedReason reason, final Long loanCollateralId) {
        super(reason.errorCode(), reason.errorMessage(), loanCollateralId);
    }

}
