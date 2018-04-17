package com.five9.openbanking.aisp.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class AccountInformationDto {
    private String accountRequestId;
    private DataDto data;
    private RiskDto rick;
    private LinkDto links;


    public AccountInformationDto() {
    }

    @JsonGetter("Data")
    public DataDto getData() {
        return data;
    }
    @JsonSetter("Data")
    public void setData(DataDto data) {
        this.data = data;
    }

    @JsonGetter("Risk")
    public RiskDto getRick() {
        return rick;
    }
    @JsonSetter("Risk")
    public void setRick(RiskDto rick) {
        this.rick = rick;
    }

    @JsonGetter("Links")
    public LinkDto getLinks() {
        return links;
    }

    @JsonSetter("Links")
    public void setLinks(LinkDto links) {
        this.links = links;
    }

    @JsonGetter("AccountRequestId")
    public String getAccountRequestId() {
        return accountRequestId;
    }
    @JsonSetter("AccountRequestId")
    public void setAccountRequestId(String accountRequestId) {
        this.accountRequestId = accountRequestId;
    }
}
