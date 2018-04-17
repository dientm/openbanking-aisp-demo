package com.five9.openbanking.aisp.service.authenticate;

import com.five9.openbanking.aisp.dto.auth.AuthResponseDto;

public interface TPPOAuth2SecurityService {

    /**
     * requesting an access token.
     */
    String getOauth2Token();
}
