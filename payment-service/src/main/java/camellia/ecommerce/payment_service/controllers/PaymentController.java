package camellia.ecommerce.payment_service.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.checkout.Session;

import camellia.ecommerce.payment_service.dtos.PaymentDto;
import camellia.ecommerce.payment_service.dtos.PaymentSessionRes;
import camellia.ecommerce.payment_service.dtos.MockPaymentReq;
import camellia.ecommerce.payment_service.entities.Payment;
import camellia.ecommerce.payment_service.mappers.PaymentMapper;
import camellia.ecommerce.payment_service.services.PaymentCRUDService;
import camellia.ecommerce.payment_service.services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    private final PaymentCRUDService paymentCRUDService;

    private final PaymentMapper paymentMapper;

    @Value("${stripe.return-url}")
    private String returnUrl;

    @GetMapping
    public ResponseEntity<UUID> findPaymentId(@RequestParam("order_id") UUID orderId) {
        Optional<Payment> paymentOpt = paymentCRUDService.findByOrderId(orderId);

        if (paymentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paymentOpt.get().getPublicId());
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<PaymentDto> findPaymentBySessionId(@PathVariable String sessionId) {
        Optional<Payment> paymentOpt = paymentCRUDService.findByStripeSessionId(sessionId);

        if (paymentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paymentMapper.toDto(paymentOpt.get()));
    }

    @GetMapping("/{paymentId}/session")
    public ResponseEntity<PaymentSessionRes> openStripeSession(@PathVariable UUID paymentId) {

        try {
            Payment payment = paymentCRUDService.findByPublicId(paymentId);
            Session session = paymentService.createPaymentSession(payment);
            return ResponseEntity.ok(new PaymentSessionRes(session.getClientSecret()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/mock/update-payment")
    public ResponseEntity<String> updateMockPayment(@Valid @RequestBody MockPaymentReq mockPaymentReq) {
        try {
            Payment payment = paymentCRUDService.findByPublicId(mockPaymentReq.paymentId());
            String mockSessionId = paymentService.updateMockPayment(payment, mockPaymentReq);
            String redirectTo = returnUrl.replace("{CHECKOUT_SESSION_ID}", mockSessionId);
            return ResponseEntity.ok(redirectTo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
