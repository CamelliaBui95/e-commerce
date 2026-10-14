package camellia.ecommerce.payment_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/configuration")
public class PaymentConfigurationController {

    @Value("${app.payment-mode}")
    private String appPaymentMode;

    @GetMapping("/payment-mode")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok(appPaymentMode);
    }

}
