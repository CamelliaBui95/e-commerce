package camellia.ecommerce.order_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import camellia.ecommerce.order_service.dtos.ClientDto;
import camellia.ecommerce.order_service.entities.Client;
import camellia.ecommerce.order_service.mappers.ClientMapper;
import camellia.ecommerce.order_service.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public Client create(ClientDto clientDto) {
        Client newClient = clientMapper.toEntity(clientDto);

        newClient.setPublicId(UUID.randomUUID());

        return clientRepository.save(newClient);
    }
}
