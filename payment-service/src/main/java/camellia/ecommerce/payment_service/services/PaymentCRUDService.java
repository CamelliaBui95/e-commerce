package camellia.ecommerce.payment_service.services;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import camellia.ecommerce.payment_service.entities.Payment;
import camellia.ecommerce.payment_service.enums.PaymentStatus;
import camellia.ecommerce.payment_service.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCRUDService {

    private final PaymentRepository paymentRepository;

    public Payment create(UUID orderId, BigDecimal amount) {
        Payment newPayment = new Payment();

        newPayment.setPublicId(UUID.randomUUID());
        newPayment.setOrderId(orderId);
        newPayment.setAmount(amount);
        newPayment.setStatus(PaymentStatus.PENDING);
        newPayment.setCreatedAt(ZonedDateTime.now());

        return save(newPayment);
    }

    public Payment findByPublicId(UUID publicId) {
        return paymentRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found: " + publicId));
    }

    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public boolean existsForOrder(UUID orderId) {
        return paymentRepository.existsByOrderId(orderId);
    }

    public Payment updateStatus(Payment payment, PaymentStatus status) {
        payment.setStatus(status);
        payment.setProcessedAt(ZonedDateTime.now());

        return save(payment);
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

}
