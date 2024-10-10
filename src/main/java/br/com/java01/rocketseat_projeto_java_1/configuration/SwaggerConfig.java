package br.com.java01.rocketseat_projeto_java_1.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Courses API")
                        .description("API responsável pelo gerenciamento de cursos")
                        .version("1"));
    }
}
