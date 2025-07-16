package com.example.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.ecommerce.dto.AddressDto;
import com.example.ecommerce.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    
    AddressDto toDto(Address address);
    
    List<AddressDto> toDtoList(List<Address> addresses);
    
    Address toEntity(AddressDto addressDto);
} 