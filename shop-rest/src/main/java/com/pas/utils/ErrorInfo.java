package com.pas.utils;

public enum ErrorInfo {
        LOGIN_TAKEN("Login already taken"),
        ENTITY_NOT_FOUND_MESSAGE("Entity with given ID doesn't exist"),
        ENTITY_ALREADY_EXIST_MESSAGE("Entity with given ID already exist"),
        RENT_CUSTOMER_SUSPENDED("Can't create rent, customer suspended"),
        ORDER_VIOLATED_BUSINESS_LOGIC("Couldn't create rent, violated business logic"),
        PRODUCT_IN_UNFINISHED_RENT("Cant delete item present in unfinished rent"),

        PASSWORD_MISMATCH("Provided current password is invalid"),

        TOKEN_NOT_PROVIDED("Authorization token not provied"),

        UNAUTHORIZED("Unauthorized"),

        BAD_REQUEST("Bad request");

        public String getValue() {
                return value;
        }

        private final String value;

        ErrorInfo(String value) {
                this.value = value;
        }
}


