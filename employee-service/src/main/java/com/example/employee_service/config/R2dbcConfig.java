package com.example.employee_service.config;

import com.example.employee_service.converter.RoleToStringConverter;
import com.example.employee_service.converter.StringToRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.data.r2dbc.dialect.DialectResolver;

import java.util.List;

// регистрация конвертеров
@Configuration("R2dbcCustomConfig ")
public class R2dbcConfig {

    @Bean("r2dbcCustomConversionsCustom")
    public R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory cf) {
        var dialect = DialectResolver.getDialect(cf);
        List<Converter<?, ?>> converters = List.of(
                new RoleToStringConverter(),
                new StringToRoleConverter()
        );
        return R2dbcCustomConversions.of(dialect, converters);
    }
}

