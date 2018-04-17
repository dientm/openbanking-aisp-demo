package com.five9.openbanking.aisp.service.authenticate;

import com.five9.openbanking.aisp.dto.auth.AuthResponseDto;

public interface PSUOauth2SecurityService {

    String getOauth2Token(String code);
}
