package com.five9.openbanking.aisp.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

public class RiskDto {
    private String someThings;

    public RiskDto(String someThings) {
        this.someThings = someThings;
    }

    public RiskDto() {
    }

    public String getSomeThings() {
        return someThings;
    }

    public void setSomeThings(String someThings) {
        this.someThings = someThings;
    }
}
