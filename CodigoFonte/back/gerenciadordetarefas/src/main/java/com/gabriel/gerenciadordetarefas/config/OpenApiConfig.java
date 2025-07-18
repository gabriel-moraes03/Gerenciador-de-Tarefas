package com.gabriel.gerenciadordetarefas.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("API Gerenciador de Tarefas")
                        .version("v1")
                        .description("Documentação da API do Gerenciador de Tarefas"));
    }
}
