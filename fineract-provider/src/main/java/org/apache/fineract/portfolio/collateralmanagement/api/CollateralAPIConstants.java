package org.apache.fineract.portfolio.collateralmanagement.api;

public final class CollateralAPIConstants {

    public CollateralAPIConstants() {

    }

    public enum CollateralJSONinputParams {
        NAME("name"), QUALITY("quality"), BASE_PRICE("basePrice"), UNIT_TYPE("unitType"),
        PCT_TO_BASE("pctToBase"), CURRENCY("currency");

        private final String value;

        CollateralJSONinputParams(final String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return name().toString().replaceAll("_", " ");
        }

        public String getValue() {
            return this.value;
        }

    }


}
