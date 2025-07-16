package com.example.ecommerce.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import com.example.ecommerce.dto.SizeDto;
import com.example.ecommerce.model.Size;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    
    SizeDto toDto(Size size);
    
    List<SizeDto> toDtoList(List<Size> sizes);
    
    Set<SizeDto> toDtoSet(Set<Size> sizes);
    
    Size toEntity(SizeDto sizeDto);
} 