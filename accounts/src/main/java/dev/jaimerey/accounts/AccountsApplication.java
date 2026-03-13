package dev.jaimerey.accounts;

import dev.jaimerey.accounts.dto.AccountsContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = AccountsContactInfoDto.class)
@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(
        title = "Accounts microservice REST API Documentation",
        version = "v1.0",
        description = "Banca Rey Accounts microservice REST API documentation",
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "Jaime Rey",
            email = "jaimereycasado@gmail.com"
        ),
        license = @io.swagger.v3.oas.annotations.info.License(
            name = "MIT License",
            url = "https://opensource.org/license/mit/"
        )

    )

)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
