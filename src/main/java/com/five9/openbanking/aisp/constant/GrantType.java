package com.five9.openbanking.aisp.constant;

public enum  GrantType implements IStatus {
    AUTHORIZATION_CODE(0, "AUTHORIZATION_CODE"),
    CLIENT_CREDENTIALS(1, "client_credentials"),
    RESOURCE_OWNER_PASSWORD_CREDENTIALS(2, "RESOURCE_OWNER_PASSWORD_CREDENTIALS"),
    REFRESH_TOKEN(3, "REFRESH_TOKEN");


    private int code;
    private String description;

    private GrantType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return this.name();
    }
}
