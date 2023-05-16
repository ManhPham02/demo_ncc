package com.example.emp.common.config;

import com.example.emp.dto.RoleDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalDatasourceConfig {
    @Bean
    public RoleDTO localDatasource() {
        return new RoleDTO("Local object, Chỉ khởi tạo khi 'local' profile active");
    }

}
