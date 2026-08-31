package camellia.ecommerce.payment_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import camellia.ecommerce.payment_service.entities.Payment;
import camellia.ecommerce.payment_service.kafka.events.PaymentEvent;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "paymentId", source = "publicId")
    PaymentEvent toEvent(Payment payment);
}
