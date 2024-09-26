package com.example.fraudprevention.service;

import com.stripe.model.Charge;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Service
public class FraudDetectionService {

    public boolean isFraudulent(Charge charge) {
        if (charge.getAmount() > 500000) { // Amount is in cents
            return true;
        } else if(isFraudDetectedByML(charge)) {
            return true;
        } else if (charge.getSource() instanceof com.stripe.model.Card card) {
            return "NG".equals(card.getCountry());  // Checking for Nigeria as an example
        } else {
            return false;
        }
    }

    public boolean isFraudDetectedByML(Charge charge) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("Time", System.currentTimeMillis() / 1000.0);
        requestPayload.put("Amount", charge.getAmount() / 100.0);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:5000/predict", request, Map.class);

        double fraudProbability = (double) response.getBody().get("fraud_probability");

        return fraudProbability > 0.8;  // Example threshold
    }

    public void sendFraudAlert(String transactionId) {
        Message message = Message.creator(
                new PhoneNumber("+1234567890"),  // Business phone number
                new PhoneNumber("+0987654321"),  // Twilio number
                "Fraud Alert: Transaction " + transactionId + " is flagged as fraudulent."
        ).create();
    }
}
