package com.dw.poc.reactive.configuration;

import io.asyncer.r2dbc.mysql.MySqlConnectionFactoryProvider;
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.ZoneId;

@Configuration
@Profile("mysql")
public class MySqlConfiguration {

    @Bean
    public ConnectionFactoryOptionsBuilderCustomizer mysqlCustomizer() {
        return (builder ->
                builder.option(MySqlConnectionFactoryProvider.SERVER_ZONE_ID,
                        ZoneId.of("UTC")));
    }
}
