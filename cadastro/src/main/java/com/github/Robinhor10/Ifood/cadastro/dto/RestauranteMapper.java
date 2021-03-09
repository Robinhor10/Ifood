package com.github.Robinhor10.Ifood.cadastro.dto;

import com.github.Robinhor10.Ifood.Restaurante;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nomeFantasia" )
    public Restaurante toRestaurante(AdicionarRestauranteDTO dto);
    
}
