package com.example.fraudprevention.service;

import com.stripe.model.Charge;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;

@Service
public class FraudDetectionService {

    public boolean isFraudulent(Charge charge) {
        // Simple rule: Flag transactions over $5000
        if (charge.getAmount() > 500000) { // Amount is in cents
            return true;
        }

        // Check if the source is a card and flag high-risk country
        if (charge.getSource() instanceof com.stripe.model.Card card) {
            return "NG".equals(card.getCountry());  // Checking for Nigeria as an example
        }

        return false;
    }

    public void sendFraudAlert(String transactionId) {
        Message message = Message.creator(
                new PhoneNumber("+1234567890"),  // Business phone number
                new PhoneNumber("+0987654321"),  // Twilio number
                "Fraud Alert: Transaction " + transactionId + " is flagged as fraudulent."
        ).create();
    }
}
