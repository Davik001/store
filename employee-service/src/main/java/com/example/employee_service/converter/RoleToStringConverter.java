package com.example.employee_service.converter;

import com.example.employee_service.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class RoleToStringConverter implements Converter<Role, String> {
    @Override
    public String convert(Role source) {
        // возвращаем имя enum — будет записано в VARCHAR-колонку
        return source.name();
    }
}
