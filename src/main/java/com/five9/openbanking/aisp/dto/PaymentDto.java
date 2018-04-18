package com.five9.openbanking.aisp.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.gson.annotations.Expose;

@JsonRootName("Data")
public class PaymentDto {
    @Expose
    private String paymentId;
    @Expose
    private String status;
    @Expose
    private String creationDateTime;
    @Expose
    private String paymentSubmissionId;


    public PaymentDto() {
    }

    @JsonGetter("PaymentId")
    public String getPaymentId() {
        return paymentId;
    }
    @JsonSetter("PaymentId")
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @JsonGetter("Status")
    public String getStatus() {
        return status;
    }

    @JsonSetter("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonGetter("CreationDateTime")
    public String getCreationDateTime() {
        return creationDateTime;
    }
    @JsonSetter("CreationDateTime")
    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @JsonGetter("PaymentSubmissionId")
    public String getPaymentSubmissionId() {
        return paymentSubmissionId;
    }

    @JsonSetter("PaymentSubmissionId")
    public void setPaymentSubmissionId(String paymentSubmissionId) {
        this.paymentSubmissionId = paymentSubmissionId;
    }
}
