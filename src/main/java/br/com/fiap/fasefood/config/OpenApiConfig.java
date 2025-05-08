package br.com.fiap.fasefood.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition()
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI faseFood() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fase Food")
                        .description("API de gerenciamento de usuário da Fase Food")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/MSFelisberto/fasefood"))
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor local de desenvolvimento")
                ));
    }
}
