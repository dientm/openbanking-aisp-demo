package com.five9.openbanking.aisp.service.PaymentAPIService.impl;

import com.five9.openbanking.aisp.AispDemoApplication;
import com.five9.openbanking.aisp.constant.Constants;
import com.five9.openbanking.aisp.dto.DataDto;
import com.five9.openbanking.aisp.dto.PaymentDto;

import com.five9.openbanking.aisp.service.PaymentAPIService.PaymentService;
import com.five9.openbanking.aisp.service.authenticate.PSUOauth2SecurityService;
import com.five9.openbanking.aisp.service.authenticate.TPPOAuth2SecurityService;
import com.google.gson.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    TPPOAuth2SecurityService tppoAuth2SecurityService;

    @Autowired
    PSUOauth2SecurityService psuOauth2SecurityService;

    @Override
    public String setupPaymentRequest() {
        // 1. Obtain an access token to invoke the Payments API
        String tppOAuth2AccessToken = tppoAuth2SecurityService.getOauth2Token();

        // 2. Invoke the Payments API to obtain a payment ID
        String paymentId = createPaymentRequest(tppOAuth2AccessToken);
        // then save paymentId to the database for query
        // generate state distinguish for each payment
        String state = UUID.randomUUID().toString();
        AispDemoApplication.statePaymentCache.put(state, paymentId);
        // create jwt requset
        //openbanking_intent_id = paymentId
        String jwtPayload = createOIDCRequest(state, paymentId);

        // create user's consent link
        String consentLink = "https://api.eu.apiconnect.ibmcloud.com/cmarcoliukibmcom-open-banking-aggregator/rw-sandbox-production/psuoauth2security/v1.0.5/oauth2/authorize?" +
                "response_type=code&" +
                "client_id=" + Constants.CLIENT_ID + "&" +
                "state=" + state +  "&" +
                "scope=payment%20openid&" +
                "redirect_uri=http://localhost:8080/redirect&" +
                "nonce=4987594875485-j&" +
                "request=" + jwtPayload;
        // after that, redirect user to consent page
        return consentLink;
    }

    private String createPaymentRequest(String accessToken) {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String payload = "{\"Data\":{\"Initiation\":{\"InstructionIdentification\":\"5791997839278080\",\"EndToEndIdentification\":\"8125371765489664\",\"InstructedAmount\":{\"Amount\":\"700.00\",\"Currency\":\"GBP\"},\"DebtorAgent\":{\"SchemeName\":\"BICFI\",\"Identification\":\"1313908532969472\"},\"DebtorAccount\":{\"SchemeName\":\"IBAN\",\"Identification\":\"6689126562660352\",\"Name\":\"John Smith\",\"SecondaryIdentification\":\"6686302651023360\"},\"CreditorAgent\":{\"SchemeName\":\"UKSortCode\",\"Identification\":\"3846889942286336\"},\"CreditorAccount\":{\"SchemeName\":\"IBAN\",\"Identification\":\"2773743013199872\",\"Name\":\"Lucy Blue\",\"SecondaryIdentification\":\"8380390651723776\"},\"RemittanceInformation\":{\"Unstructured\":\"emeherpakkaodafeofiu\",\"Reference\":\"ehoorepre\"}}},\"Risk\":{\"PaymentContextCode\":\"PersonToPerson\",\"MerchantCategoryCode\":\"nis\",\"MerchantCustomerIdentification\":\"1130294929260544\",\"DeliveryAddress\":{\"AddressLine\":[\"totbelsanagrusa\"],\"StreetName\":\"Morning Road\",\"BuildingNumber\":\"62\",\"PostCode\":\"G3 5HY\",\"TownName\":\"Glasgow\",\"CountrySubDivision\":[\"Scotland\"],\"Country\":\"UK\"}}}";
            HttpEntity<String> request = new HttpEntity<String>(payload, headers);

            String endpoint = "https://api.eu.apiconnect.ibmcloud.com/cmarcoliukibmcom-open-banking-aggregator/rw-sandbox-production/open-banking/v1.0.5/payments";
            ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            // TODO: parser json to object paymentdto
            PaymentDto paymentDto = gson.fromJson(response.getBody(), PaymentDto.class);

            // TODO: need to remove
            JsonElement jsonElement = new JsonParser().parse(response.getBody());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            jsonObject = jsonObject.getAsJsonObject("Data");
            String paymentId = jsonObject.get("PaymentId").getAsString();

            return paymentId;

    }


    public String submitPayment(String code, String state) {


        // TODO: below step should be after constenting user
        // 4. Get the access token required to execute the payment
        String psuAccessToken = psuOauth2SecurityService.getOauth2Token(code);

        // 5. Introspect the access token (optional)

        // 6. Submit the payment for execution
        // state => paymentId
        String paymentId = AispDemoApplication.statePaymentCache.get(state);
        String paymentSubmissionId = _submitPayment(psuAccessToken, paymentId);
        return paymentSubmissionId;
    }


    private String _submitPayment(String token, String paymentId) {
        // Setup headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);


        String payload = "{\"Data\":{\"PaymentId\":\"1502284035662\", \"Initiation\":{\"InstructionIdentification\":\"5791997839278080\",\"EndToEndIdentification\":\"8125371765489664\",\"InstructedAmount\":{\"Amount\":\"900.00\",\"Currency\":\"GBP\"},\"DebtorAgent\":{\"SchemeName\":\"BICFI\",\"Identification\":\"1313908532969472\"},\"DebtorAccount\":{\"SchemeName\":\"IBAN\",\"Identification\":\"6689126562660352\",\"Name\":\"John Smith\",\"SecondaryIdentification\":\"6686302651023360\"},\"CreditorAgent\":{\"SchemeName\":\"UKSortCode\",\"Identification\":\"3846889942286336\"},\"CreditorAccount\":{\"SchemeName\":\"IBAN\",\"Identification\":\"2773743013199872\",\"Name\":\"Lucy Blue\",\"SecondaryIdentification\":\"8380390651723776\"},\"RemittanceInformation\":{\"Unstructured\":\"emeherpakkaodafeofiu\",\"Reference\":\"ehoorepre\"}}},\"Risk\":{\"PaymentContextCode\":\"PersonToPerson\",\"MerchantCategoryCode\":\"nis\",\"MerchantCustomerIdentification\":\"1130294929260544\",\"DeliveryAddress\":{\"AddressLine\":[\"totbelsanagrusa\"],\"StreetName\":\"Morning Road\",\"BuildingNumber\":\"62\",\"PostCode\":\"G3 5HY\",\"TownName\":\"Glasgow\",\"CountrySubDivision\":[\"Scotland\"],\"Country\":\"UK\"}}}";
        payload = payload.replace("1502284035662", paymentId);
        HttpEntity<String> request = new HttpEntity<>(payload, headers);
        RestTemplate restTemplate = new RestTemplate();

        String endpoint = "https://api.eu.apiconnect.ibmcloud.com/cmarcoliukibmcom-open-banking-aggregator/rw-sandbox-production/open-banking/v1.0.5/payment-submissions";


        System.out.println("authorization Bearer " + token);
        System.out.println("paymentId " + paymentId);
        System.out.println("payload " + payload);
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);


        // TODO: need to remove
        JsonElement jsonElement = new JsonParser().parse(response.getBody());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject = jsonObject.getAsJsonObject("Data");
        String paymentSubscriptionId = jsonObject.get("PaymentSubmissionId").getAsString();

        return paymentSubscriptionId;
    }
    private String createOIDCRequest(String state, String paymentId) {
        String samplePayload = "{\"iss\":\"https://api.alphanbank.com\",\"aud\":\"s6BhdRkqt3\",\"response_type\":\"code\",\"client_id\":\"b218b2bb-98b7-4237-a2bc-8e1dd3aae3bf\",\"redirect_uri\":\"http://localhost:8080/redirect\",\"scope\":\"openid payments\",\"state\":\"123456\",\"nonce\":\"4987594875485-j\",\"max_age\":86400,\"claims\":{\"userinfo\":{\"openbanking_intent_id\":{\"value\":\"1504021465815\",\"essential\":true}},\"id_token\":{\"openbanking_intent_id\":{\"value\":\"1524037032416\",\"essential\":true},\"acr\":{\"essential\":true,\"values\":[\"urn:openbanking:psd2:sca\",\"urn:openbanking:psd2:ca\"]}}},\"jti\":\"658fc851-777c-4521-9f52-30a49c7f4238\",\"iat\":1502467982,\"exp\":1502471658}";


        samplePayload = samplePayload.replace("123456", state);
        samplePayload = samplePayload.replace("1524037032416", paymentId);

        String jwtPayload = Jwts.builder()
                .setPayload(samplePayload)
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();

        System.out.println("jwt payload: " + jwtPayload);
        return jwtPayload;

    }
}



