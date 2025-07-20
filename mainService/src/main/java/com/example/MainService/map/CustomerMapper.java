package com.example.MainService.map;


import com.example.MainService.dto.common.CustomerResponseDTO;
import com.example.MainService.dto.create.CustomerCreateDTO;
import com.example.MainService.dto.update.CustomerUpdateDTO;
import com.example.MainService.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface CustomerMapper {
    Customer toEntity(CustomerCreateDTO customerCreateDTO);
    Customer toEntity(CustomerUpdateDTO customerUpdateDTO, @MappingTarget Customer customer);
    CustomerResponseDTO toDto(Customer customer);
}
