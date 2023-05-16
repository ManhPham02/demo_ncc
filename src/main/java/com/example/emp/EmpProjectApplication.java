package com.example.emp;

import com.example.emp.dto.Mail;
import com.example.emp.dto.RoleDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableConfigurationProperties
public class EmpProjectApplication {
    public static void main(String[] args) {
//        SpringApplication.run(EmpProjectApplication.class, args);
        SpringApplication application = new SpringApplication(EmpProjectApplication.class);
        ApplicationContext context = application.run(args);

        RoleDTO localDatasource = context.getBean(RoleDTO.class);
        System.out.println(localDatasource);

    }
}
