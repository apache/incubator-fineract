package org.apache.fineract.integrationtests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.fineract.integrationtests.common.CollateralManagementHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientCollateralIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(ClientCollateralIntegrationTest.class);
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;

    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    @Test
    public void createClientCollateralTest() {
        final Integer collateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        LOG.info("Creating Client Collateral");
        updateClientCollateral(collateralId);
    }

    private void updateClientCollateral(final Integer collateralId) {
        final String updateClientCollateral = CollateralManagementHelper.updateClientCollateralAsString(this.requestSpec, this.responseSpec,
                collateralId);
        Assertions.assertEquals(collateralId, updateClientCollateral);
    }

}
