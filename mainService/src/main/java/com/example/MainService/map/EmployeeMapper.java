package com.example.MainService.map;


import com.example.MainService.dto.common.EmployeeResponseDTO;
import com.example.MainService.dto.create.EmployeeCreateDTO;
import com.example.MainService.dto.update.EmployeeUpdateDTO;
import com.example.MainService.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeResponseDTO toResponseDTO(Employee employee);
    Employee toEntity(EmployeeCreateDTO employeeCreateDTO);
    Employee toEntity(EmployeeUpdateDTO employeeUpdateDTO);
}