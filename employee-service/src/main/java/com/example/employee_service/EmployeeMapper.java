package com.example.employee_service;

import com.example.employee_service.dto.CreateEmp;
import com.example.employee_service.dto.Emp;
import com.example.employee_service.dto.ResponseEmp;
import com.example.employee_service.dto.UpdateEmp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

//    Emp toDto(Employee employee);
//
//    ResponseEmp toResponseDto(Employee employee);
//
//    List<ResponseEmp> toResponseDtoList(List<Employee> employees);
//
//    Employee toEntity(Emp dto);
//
//    Employee toEntity(CreateEmp createDto);
//
//    void updateFromDto(UpdateEmp updateDto, @MappingTarget Employee employee);

    // Создание сущности из CreateEmp
    Employee toEntity(CreateEmp dto);

    // Обновление сущности из UpdateEmp
    void updateEntityFromDto(UpdateEmp dto, @MappingTarget Employee entity);

    // Преобразование Entity в Response DTO
    ResponseEmp toResponseEmp(Employee entity);

}
