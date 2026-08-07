package camellia.ecommerce.order_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import camellia.ecommerce.order_service.dtos.ClientDto;
import camellia.ecommerce.order_service.entities.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientDto clientDto);

    ClientDto toDto(Client client);
}
