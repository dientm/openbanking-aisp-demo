package com.five9.openbanking.aisp.dto.auth;

import com.five9.openbanking.aisp.constant.GrantType;
import com.five9.openbanking.aisp.constant.Scope;

public class AuthRequestDto {
    private String clientId;
    private String clientSecret;
    private GrantType grantType;
    private Scope scope;
    private String code;
    private String redirectUri;


    public AuthRequestDto() {

    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(GrantType grantType) {
        this.grantType = grantType;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
