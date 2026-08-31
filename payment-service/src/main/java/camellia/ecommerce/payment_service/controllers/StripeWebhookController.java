package camellia.ecommerce.payment_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments/webhook")
public class StripeWebhookController {
    
    @PostMapping
    public ResponseEntity<Void> handle(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) {
        //TODO: process POST request
        
        return ResponseEntity.ok().build();
    }
    
}
