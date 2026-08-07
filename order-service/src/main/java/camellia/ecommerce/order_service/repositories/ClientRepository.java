package camellia.ecommerce.order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import camellia.ecommerce.order_service.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
