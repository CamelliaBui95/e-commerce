package camellia.ecommerce.payment_service.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import camellia.ecommerce.payment_service.entities.Payment;
import camellia.ecommerce.payment_service.services.PaymentCRUDService;
import camellia.ecommerce.payment_service.services.PaymentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    private final PaymentCRUDService paymentCRUDService;

    @GetMapping
    public ResponseEntity<UUID> findPaymentId(@RequestParam("order_id") UUID orderId) {
        Optional<Payment> paymentOpt = paymentCRUDService.findByOrderId(orderId);

        if (paymentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paymentOpt.get().getPublicId());
    }
}
