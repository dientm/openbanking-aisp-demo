package com.five9.openbanking.aisp.constant;

public enum  Scope implements IStatus {
    TPP_CLIENT_CREDENTIAL(0, "tpp_client_credential");


    private int code;
    private String description;

    private Scope(int code, String description) {
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
