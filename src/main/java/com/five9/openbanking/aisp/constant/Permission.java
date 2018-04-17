package com.five9.openbanking.aisp.constant;

public enum Permission implements IStatus {
    READ_ACCOUNT_DETAIL(0, "ReadAccountDetail"),
    READ_BALANCERS(1, "ReadBalances"),
    READ_BENEFICIARIES_DETAIL(2, "ReadBeneficiariesDetail"),
    READ_DIRECT_DEBITS(3, "ReadDirectDebits"),
    READ_PRODUCTS(4, "ReadProducts"),
    READ_STANDING_ORDERS_DETAIL(5, "ReadStandingOrdersDetail"),
    READ_TRANSACTIONS_CREADITS(6, "ReadTransactionsCredits"),
    READ_TRANSACTIONS_DEBITS(7, "ReadTransactionsDebits"),
    READ_TRANSACTIONS_DETAIL(8, "ReadTransactionsDetail");


    private int code;
    private String description;

    private Permission(int code, String description) {
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
