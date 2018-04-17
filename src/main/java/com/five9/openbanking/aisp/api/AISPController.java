package com.five9.openbanking.aisp.api;

import com.five9.openbanking.aisp.constant.Constants;
import com.five9.openbanking.aisp.service.AIPSService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import javax.xml.ws.Response;
import java.text.ParseException;

@Controller
public class AISPController {

    @Autowired
    AccountService accountService;


/*    @RequestMapping(value = "/account",method = RequestMethod.GET)
    public ModelAndView requestAccountInforation() throws ParseException {
//        httpServletResponse.setHeader("Location", accountService.setupAccountRequest());
        return new ModelAndView("redirect:" + accountService.setupAccountRequest());
    }*/

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ResponseEntity redirect(@PathParam("code") String code) throws ParseException {
        return new ResponseEntity(accountService.setupAccountRequest(code), HttpStatus.OK);
    }

    @RequestMapping(value = "/consent", method = RequestMethod.GET)
    public RedirectView consent() {
        String consentLink = "https://api.eu.apiconnect.ibmcloud.com/cmarcoliukibmcom-open-banking-aggregator/rw-sandbox-production/psuoauth2security/v1.0.5/oauth2/authorize?" +
                "response_type=code&" +
                "client_id=" + Constants.CLIENT_ID + "&" +
                "scope=payment%20openid&" +
                "redirect_uri=http://localhost:8080/redirect&" +
                "nonce=4987594875485-j&" +
                "request=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FwaS5hbHBoYW5iYW5rLmNvbSIsImF1ZCI6InM2QmhkUmtxdDMiLCJyZXNwb25zZV90eXBlIjoiY29kZSIsImNsaWVudF9pZCI6InM2QmhkUmtxdDMiLCJyZWRpcmVjdF91cmkiOiJodHRwczovL2V4YW1wbGUuY29tL3JlZGlyZWN0Iiwic2NvcGUiOiJvcGVuaWQgcGF5bWVudHMiLCJzdGF0ZSI6ImFmMGlmanNsZGtqIiwibm9uY2UiOiI0OTg3NTk0ODc1NDg1LWoiLCJtYXhfYWdlIjo4NjQwMCwiY2xhaW1zIjp7InVzZXJpbmZvIjp7Im9wZW5iYW5raW5nX2ludGVudF9pZCI6eyJ2YWx1ZSI6IjE1MDQwMjE0NjU4MTUiLCJlc3NlbnRpYWwiOnRydWV9fSwiaWRfdG9rZW4iOnsib3BlbmJhbmtpbmdfaW50ZW50X2lkIjp7InZhbHVlIjoiMTUwNDAyMTQ2NTgxNSIsImVzc2VudGlhbCI6dHJ1ZX0sImFjciI6eyJlc3NlbnRpYWwiOnRydWUsInZhbHVlcyI6WyJ1cm46b3BlbmJhbmtpbmc6cHNkMjpzY2EiLCJ1cm46b3BlbmJhbmtpbmc6cHNkMjpjYSJdfX19LCJqdGkiOiI2NThmYzg1MS03NzdjLTQ1MjEtOWY1Mi0zMGE0OWM3ZjQyMzgiLCJpYXQiOjE1MDI0Njc5ODIsImV4cCI6MTUwMjQ3MTY1OH0.7R4HcpmV5unXSmYEOfWhhU71iqLhnbA9Q88X72KOt0s";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(consentLink);
        return redirectView;

    }
}
