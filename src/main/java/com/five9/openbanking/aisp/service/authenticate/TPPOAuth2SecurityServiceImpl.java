package com.five9.openbanking.aisp.service.authenticate;

import com.five9.openbanking.aisp.constant.Constants;
import com.five9.openbanking.aisp.constant.GrantType;
import com.five9.openbanking.aisp.constant.Scope;
import com.five9.openbanking.aisp.dto.auth.AuthRequestDto;
import com.five9.openbanking.aisp.dto.auth.AuthResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class TPPOAuth2SecurityServiceImpl implements TPPOAuth2SecurityService {
    @Override
    public String getOauth2Token() {
        AuthRequestDto authRequest = new AuthRequestDto();
        authRequest.setClientId(Constants.CLIENT_ID);
        authRequest.setClientSecret(Constants.CLIENT_SECRET);
        authRequest.setGrantType(GrantType.CLIENT_CREDENTIALS);
        authRequest.setScope(Scope.TPP_CLIENT_CREDENTIAL);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", authRequest.getGrantType().getDescription());
        map.add("client_id", authRequest.getClientId());
        map.add("client_secret", authRequest.getClientSecret());
        map.add("scope", authRequest.getScope().getDescription());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);


        String endpoint = "https://api.eu.apiconnect.ibmcloud.com/cmarcoliukibmcom-open-banking-aggregator/rw-sandbox-production/tppoauth2security/oauth2/token";
        ResponseEntity<AuthResponseDto> response = restTemplate.postForEntity(endpoint, request, AuthResponseDto.class);
        return response.getBody().getAccessToken();

    }
}
