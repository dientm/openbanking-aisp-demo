package com.five9.openbanking.aisp.service.AIPSService;

import com.five9.openbanking.aisp.dto.AccountInformationDto;

import java.text.ParseException;

public interface AccountService {
    public String setupAccountRequest(String code) throws ParseException;

    public getAvailableAccounts(String accessToken);
}
