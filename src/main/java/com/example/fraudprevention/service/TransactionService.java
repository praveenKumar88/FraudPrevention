package com.example.fraudprevention.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Value("${stripe.apiKey}")
    private String apiKey;

    public List<Charge> getAllCharges() throws StripeException {
        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 100);

        ChargeCollection charges = Charge.list(params);
        return charges.getData();
    }
}