package com.santaellamorenofrancisco.service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;

@Service
public class PaypalService {
	
	@Autowired
	private APIContext apiContext;
	
	@Autowired
	private UserService userservice;
	
	
	public Payment createPayment(
			Double total, 
			String currency, 
			String method,
			String intent,
			String description, 
			Long user_id,
			String cancelUrl, 
			String successUrl) throws Exception{
		Amount amount = new Amount();
		amount.setCurrency(currency);
		BigDecimal total2 = new BigDecimal(total);
		amount.setTotal(total2.setScale(2, RoundingMode.HALF_UP).toString());
		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId, Long user_id) throws Exception{
		Payment payment = new Payment();
		payment.setId(paymentId);

		//
		
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		System.out.println("payment:" +payment);
		System.out.println("apicontext:" +apiContext);
		System.out.println("Paymenexecute:" + paymentExecute);
		
		Payment executedPayment = payment.execute(apiContext, paymentExecute);
		userservice.addBalance(user_id,new BigDecimal(executedPayment.getTransactions().get(0).getAmount().getTotal()));
		return executedPayment;
	}

}