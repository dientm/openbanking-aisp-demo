package com.five9.openbanking.aisp.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class LinkDto {
    private String self;

    public LinkDto(String self) {
        this.self = self;
    }

    public LinkDto() {
    }

    @JsonGetter("Self")
    public String getSelf() {
        return self;
    }
    @JsonSetter("Self")
    public void setSelf(String self) {
        this.self = self;
    }
}
