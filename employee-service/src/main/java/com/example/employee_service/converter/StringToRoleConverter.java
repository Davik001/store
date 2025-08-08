package com.example.employee_service.converter;

import com.example.employee_service.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StringToRoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String source) {
        // превращаем текст обратно в enum; бросит IllegalArgumentException,
        // если в БД кто-то записал «WRONG_VALUE» — это вам сразу даст знать
        return Role.valueOf(source);
    }
}
