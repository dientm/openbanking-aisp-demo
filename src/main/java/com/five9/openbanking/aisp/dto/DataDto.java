package com.five9.openbanking.aisp.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.gson.annotations.Expose;


public class DataDto {
    @Expose
    private String[] permissions;
    @Expose
    private String expirationDateTime;
    @Expose
    private String transactionFromDateTime;
    @Expose
    private String transactionToDateTime;

    private String status;

    public DataDto(String[] permissions, String expirationDateTime, String transactionFromDateTime, String transactionToDateTime) {
        this.permissions = permissions;
        this.expirationDateTime = expirationDateTime;
        this.transactionFromDateTime = transactionFromDateTime;
        this.transactionToDateTime = transactionToDateTime;
    }

    public DataDto() {
    }

    @JsonGetter("Permissions")
    public String[] getPermissions() {
        return permissions;
    }
    @JsonSetter("Permissions")
    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
    public void setExpirationDateTime(String expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }
    @JsonSetter("TransactionFromDateTime")
    public void setTransactionFromDateTime(String transactionFromDateTime) {
        this.transactionFromDateTime = transactionFromDateTime;
    }

    @JsonGetter("TransactionFromDateTime")
    public String getTransactionFromDateTime() {
        return transactionFromDateTime;
    }

    @JsonGetter("TransactionToDateTime")
    public String getTransactionToDateTime() {
        return transactionToDateTime;
    }

    public void setTransactionToDateTime(String transactionToDateTime) {
        this.transactionToDateTime = transactionToDateTime;
    }

    @JsonGetter("ExpirationDateTime")
    public String getExpirationDateTime() {
        return expirationDateTime;
    }

    @JsonGetter("Status")
    public String getStatus() {
        return status;
    }
    @JsonSetter("Status")
    public void setStatus(String status) {
        this.status = status;
    }

}
