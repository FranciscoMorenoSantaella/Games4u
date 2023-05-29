package com.santaellamorenofrancisco.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.santaellamorenofrancisco.model.Pay;
import com.santaellamorenofrancisco.service.PaypalService;

@RestController
@RequestMapping("paypal/")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
public class PaypalController {

    @Autowired
    PaypalService service;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @PostMapping("/pay")
    public ResponseEntity<?> payment(@RequestBody Pay pay) {
        try {
            Payment payment = service.createPayment(pay.getPrice(), pay.getCurrency(), pay.getMethod(),
                pay.getIntent(), pay.getDescription(), "http://localhost:8080/" + CANCEL_URL,
                "http://localhost:8080/" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.status(HttpStatus.OK).body(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = CANCEL_URL)
    public ResponseEntity<?> cancelPay() {
        return ResponseEntity.ok("cancel");
    }

    @GetMapping(value = "/pay/success")
    public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok("success");
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
