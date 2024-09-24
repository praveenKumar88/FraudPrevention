package com.example.fraudprevention.controller;

import com.example.fraudprevention.service.FraudDetectionService;
import com.example.fraudprevention.service.TransactionService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fraud")
public class FraudController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @GetMapping("/check")
    public List<String> checkFraudulentTransactions() throws StripeException {
        List<Charge> charges = transactionService.getAllCharges();
        List<String> fraudulentTransactions = new ArrayList<>();

        for (Charge charge : charges) {
            if (fraudDetectionService.isFraudulent(charge)) {
                fraudulentTransactions.add(charge.getId());
//                fraudDetectionService.sendFraudAlert(charge.getId());
            }
        }
        return fraudulentTransactions;
    }
}
