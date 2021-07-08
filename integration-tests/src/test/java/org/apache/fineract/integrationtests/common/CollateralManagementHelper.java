package org.apache.fineract.integrationtests.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.math.BigDecimal;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollateralManagementHelper {

    private static final Logger LOG = LoggerFactory.getLogger(CollateralManagementHelper.class);
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;

    public CollateralManagementHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }

    public static Integer createClientCollateral(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        final Integer clientID = ClientHelper.createClient(requestSpec, responseSpec);
        ClientHelper.verifyClientCreatedOnServer(requestSpec, responseSpec, clientID);

        final Integer resourceId = createCollateralProduct(requestSpec, responseSpec);

        return createClientCollateral(requestSpec, responseSpec, String.valueOf(clientID), resourceId);
    }

    public static Integer createClientCollateral(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String clientId, final Integer collateralId) {
        LOG.info("---------------------------------CREATING A CLIENT_COLLATERAL---------------------------------------------");
        final String CLIENT_COLLATERAL_URL = "/fineract-provider/api/v1/clients/" + clientId + "/collaterals" + "?"
                + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPost(requestSpec, responseSpec, CLIENT_COLLATERAL_URL,
                clientCollateralAsJson(collateralId, BigDecimal.valueOf(100)), "resourceId");
    }

    public static Object getClientCollateralData(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer collateralId, final String clientId) {
        final String CLIENT_COLLATERAL_URL = "/fineract-provider/api/v1/clients" + clientId + "/collaterals/" + collateralId + "?"
                + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, CLIENT_COLLATERAL_URL, "quantity");
    }

    public static String clientCollateralAsJson(final Integer collateralId, final BigDecimal quantity) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("collateralId", collateralId);
        map.put("quantity", quantity);
        LOG.info("map :  {}", map);
        return new Gson().toJson(map);
    }

    public static Integer createCollateralProduct(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        LOG.info("---------------------------------CREATING A COLLATERAL_PRODUCT---------------------------------------------");
        final String COLLATERAL_PRODUCT = "/fineract-provider/api/v1/collateral-management" + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPost(requestSpec, responseSpec, COLLATERAL_PRODUCT, collateralProductAsJson("Land Property New", "USD",
                "acre", "agriculture", BigDecimal.valueOf(40), BigDecimal.valueOf(100000000), "en"), "resourceId");
    }

    public static String collateralProductAsJson(final String name, final String currency, final String unitType, final String quality,
            final BigDecimal pctToBase, final BigDecimal baseAmount, final String locale) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("currency", currency);
        map.put("unitType", unitType);
        map.put("quality", quality);
        map.put("pctToBase", pctToBase);
        map.put("basePrice", baseAmount);
        map.put("locale", locale);
        LOG.info("map :  {}", map);
        return new Gson().toJson(map);
    }

    public static Object updateCollateralProduct(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer collateralId) {
        LOG.info("---------------------------------UPDATING A COLLATERAL_PRODUCT---------------------------------------------");
        final String COLLATERAL_PRODUCT_URL = "/fineract-provider/api/v1/collateral-management/" + collateralId + "?"
                + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPut(requestSpec, responseSpec, COLLATERAL_PRODUCT_URL, updateCollateralProductAsJson("Land Property New",
                "USD", "acre", "agriculture", BigDecimal.valueOf(40), BigDecimal.valueOf(100000), "en"), "changes");
    }

    public static String updateCollateralProductAsString(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer collateralId) {

        Object updateCollateralObject = updateCollateralProduct(requestSpec, responseSpec, collateralId);
        // Convert the Object to String and fetch updated value
        Gson gson = new Gson();
        String result = gson.toJson(updateCollateralObject);
        JsonObject reportObject = JsonParser.parseString(result).getAsJsonObject();
        String value = reportObject.get("value").getAsString();

        return value;
    }

    public static String updateCollateralProductAsJson(final String name, final String currency, final String unitType,
            final String quality, final BigDecimal pctToBase, final BigDecimal baseAmount, final String locale) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("currency", currency);
        map.put("unitType", unitType);
        map.put("quality", quality);
        map.put("pctToBase", pctToBase);
        map.put("basePrice", baseAmount);
        map.put("locale", locale);
        return new Gson().toJson(map);
    }

    public static Object updateClientCollateral(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer collateralId) {
        final Integer clientID = ClientHelper.createClient(requestSpec, responseSpec);
        ClientHelper.verifyClientCreatedOnServer(requestSpec, responseSpec, clientID);
        LOG.info("---------------------------------UPDATING A COLLATERAL_PRODUCT---------------------------------------------");
        final String CLIENT_COLLATERAL_URL = "/fineract-provider/api/v1/clients/" + clientID + "/collaterals/" + collateralId + "?"
                + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPut(requestSpec, responseSpec, CLIENT_COLLATERAL_URL,
                updateClientCollateralAsJson(collateralId, BigDecimal.valueOf(1), ""), "changes");
    }

    public static String updateClientCollateralAsString(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer collateralId) {

        Object configurationObject = updateClientCollateral(requestSpec, responseSpec, collateralId);
        // Convert the Object to String and fetch updated value
        Gson gson = new Gson();
        String result = gson.toJson(configurationObject);
        JsonObject reportObject = JsonParser.parseString(result).getAsJsonObject();
        String value = reportObject.get("resourceId").getAsString();

        return value;
    }

    public static String updateClientCollateralAsJson(final Integer collateralId, final BigDecimal quantity, final String quality) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("id", collateralId);
        map.put("quantity", quantity);
        map.put("quality", quality);
        LOG.info("map :  {}", map);
        return new Gson().toJson(map);
    }

}
