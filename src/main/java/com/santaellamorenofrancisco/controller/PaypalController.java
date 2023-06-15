package com.santaellamorenofrancisco.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
public class PaypalController {

    @Autowired
    PaypalService service;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @PostMapping("/pay")
    public ResponseEntity<?> payment(@RequestBody Pay pay) throws Exception {
        try {
            Payment payment = service.createPayment(pay.getPrice(), pay.getCurrency(), pay.getMethod(),
                pay.getIntent(), pay.getDescription(), pay.getUser_id(), "https://games4u-62a8f.web.app/" + CANCEL_URL,
                "https://games4u-62a8f.web.app/" + SUCCESS_URL + "?user_id=" + pay.getUser_id());
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
    	return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "https://games4u-62a8f.web.app/perfil/?message=Pago cancelado&success=false")
                .build();
    }

    @GetMapping(value = SUCCESS_URL)
    public ResponseEntity successPay(@RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId, @RequestParam("user_id") Long user_id) throws Exception {
        try {
            Payment payment = service.executePayment(paymentId, payerId,user_id);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
            	return ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", "https://games4u-62a8f.web.app/perfil/?message=Pago realizado correctamente&success=true")
                        .build();
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
