package com.five9.openbanking.aisp.api;

import com.five9.openbanking.aisp.constant.Constants;
import com.five9.openbanking.aisp.service.PaymentAPIService.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;
import java.text.ParseException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public RedirectView redirect(@PathParam("code") String code, @PathParam("state") String state){
        System.out.println("code: " + code);
        System.out.println("state: " + state); // state => paymentId in database
//        return new ResponseEntity<>(paymentService.submitPayment(code, state), HttpStatus.OK);
        paymentService.submitPayment(code, state);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:4200/payment-success");
        return redirectView;

    }

    @RequestMapping(value = "/payment", method = GET)
    public RedirectView consent() {
        // setup payment request then redirecting user to consent page
        String consentLink = paymentService.setupPaymentRequest();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(consentLink);
        return redirectView;

    }
}
