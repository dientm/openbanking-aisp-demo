package com.five9.openbanking.aisp.service.AIPSService.impl;

import com.five9.openbanking.aisp.constant.Constants;
import com.five9.openbanking.aisp.constant.Permission;
import com.five9.openbanking.aisp.dto.AccountInformationDto;
import com.five9.openbanking.aisp.dto.DataDto;
import com.five9.openbanking.aisp.dto.RiskDto;
import com.five9.openbanking.aisp.dto.auth.AuthResponseDto;
import com.five9.openbanking.aisp.service.AIPSService.AccountService;
import com.five9.openbanking.aisp.service.authenticate.PSUOauth2SecurityService;
import com.five9.openbanking.aisp.service.authenticate.TPPOAuth2SecurityService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    TPPOAuth2SecurityService tppoAuth2SecurityService;

    @Autowired
    PSUOauth2SecurityService psuOauth2SecurityService;

    @Override
    public String setupAccountRequest(String code) throws ParseException {
        // get access-token
        String tppOAuth2AccessToken = tppoAuth2SecurityService.getOauth2Token();

        // set up account request
        AccountInformationDto accountInformationDto = createAccountRequest(tppOAuth2AccessToken);

        // get accountRequestId
        String AccountRequestId = accountInformationDto.getAccountRequestId();

        // 4. Get the access token required to execute the payment
        String psuAccessToken = psuOauth2SecurityService.getOauth2Token(code);
        // 6. Submit the payment for execution


        return psuAccessToken;
    }

    private String createConsentLink() {
        // create consent link
        String consentLink = "https://api.eu.apiconnect.ibmcloud.com/cmarcoliukibmcom-open-banking-aggregator/rw-sandbox-production/psuoauth2security/v1.0.5/oauth2/authorize?" +
                "response_type=code&" +
                "client_id=" + Constants.CLIENT_ID + "&" +
                "scope=payment%20openid&" +
                "redirect_uri=http://localhost:8080/redirect&" +
                "nonce=4987594875485-j&" +
                "request=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FwaS5hbHBoYW5iYW5rLmNvbSIsImF1ZCI6InM2QmhkUmtxdDMiLCJyZXNwb25zZV90eXBlIjoiY29kZSIsImNsaWVudF9pZCI6InM2QmhkUmtxdDMiLCJyZWRpcmVjdF91cmkiOiJodHRwczovL2V4YW1wbGUuY29tL3JlZGlyZWN0Iiwic2NvcGUiOiJvcGVuaWQgcGF5bWVudHMiLCJzdGF0ZSI6ImFmMGlmanNsZGtqIiwibm9uY2UiOiI0OTg3NTk0ODc1NDg1LWoiLCJtYXhfYWdlIjo4NjQwMCwiY2xhaW1zIjp7InVzZXJpbmZvIjp7Im9wZW5iYW5raW5nX2ludGVudF9pZCI6eyJ2YWx1ZSI6IjE1MDQwMjE0NjU4MTUiLCJlc3NlbnRpYWwiOnRydWV9fSwiaWRfdG9rZW4iOnsib3BlbmJhbmtpbmdfaW50ZW50X2lkIjp7InZhbHVlIjoiMTUwNDAyMTQ2NTgxNSIsImVzc2VudGlhbCI6dHJ1ZX0sImFjciI6eyJlc3NlbnRpYWwiOnRydWUsInZhbHVlcyI6WyJ1cm46b3BlbmJhbmtpbmc6cHNkMjpzY2EiLCJ1cm46b3BlbmJhbmtpbmc6cHNkMjpjYSJdfX19LCJqdGkiOiI2NThmYzg1MS03NzdjLTQ1MjEtOWY1Mi0zMGE0OWM3ZjQyMzgiLCJpYXQiOjE1MDI0Njc5ODIsImV4cCI6MTUwMjQ3MTY1OH0.7R4HcpmV5unXSmYEOfWhhU71iqLhnbA9Q88X72KOt0s";
        return consentLink;
    }
    private AccountInformationDto createAccountRequest(java.lang.String accessToken) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<java.lang.String, java.lang.String> map= new LinkedMultiValueMap<java.lang.String, java.lang.String>();
        DataDto data = new DataDto();
        List<java.lang.String> permissions = new ArrayList<>();
        permissions.add(Permission.READ_ACCOUNT_DETAIL.getDescription());
        permissions.add(Permission.READ_BALANCERS.getDescription());
        permissions.add(Permission.READ_BENEFICIARIES_DETAIL.getDescription());
        permissions.add(Permission.READ_DIRECT_DEBITS.getDescription());
        permissions.add(Permission.READ_PRODUCTS.getDescription());
        permissions.add(Permission.READ_STANDING_ORDERS_DETAIL.getDescription());
        permissions.add(Permission.READ_TRANSACTIONS_CREADITS.getDescription());
        permissions.add(Permission.READ_TRANSACTIONS_DEBITS.getDescription());
        permissions.add(Permission.READ_TRANSACTIONS_DETAIL.getDescription());
        java.lang.String[] permArr = new java.lang.String[permissions.size()];
        permArr = permissions.toArray(permArr);
        data.setPermissions(permArr);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date = format.parse("2014-01-30 07:48:25");


        data.setExpirationDateTime("2017-05-02T00:00:00+00:00");
        data.setTransactionFromDateTime("2017-05-03T00:00:00+00:00");
        data.setTransactionToDateTime("2017-12-03T00:00:00+00:00");
        AccountInformationDto accountInformation = new AccountInformationDto();

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();



        accountInformation.setData(data);
        accountInformation.setRick(new RiskDto());
        java.lang.String payload = gson.toJson(accountInformation);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpEntity<java.lang.String> request = new HttpEntity<java.lang.String>(payload, headers);
        System.out.println(payload);

        java.lang.String endpoint = "https://api.eu.apiconnect.ibmcloud.com/cmarcoliukibmcom-open-banking-aggregator/rw-sandbox-production/open-banking/v1.1/account-requests";
        ResponseEntity<AccountInformationDto> response = restTemplate.postForEntity(endpoint, request, AccountInformationDto.class);
        System.out.print("break point");
        return response.getBody();


    }


}










