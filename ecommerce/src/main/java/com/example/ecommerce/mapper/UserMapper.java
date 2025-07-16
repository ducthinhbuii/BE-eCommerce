package com.example.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.Users;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface UserMapper {
    
    UserDto toDto(Users user);
    
    List<UserDto> toDtoList(List<Users> users);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "listAddress", ignore = true)
    @Mapping(target = "listPayment", ignore = true)
    @Mapping(target = "listRating", ignore = true)
    @Mapping(target = "listReview", ignore = true)
    @Mapping(target = "password", ignore = true)
    Users toEntity(UserDto userDto);
} 