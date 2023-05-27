package com.david.foro_alura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPI30
public class SpringdocConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                Contact contacto = new Contact();
                contacto.setEmail("rolondavid@outlook.com");
                contacto.setName("David Rolón");
                contacto.setUrl("https://www.linkedin.com/in/david-emanuel-rolon/");

                OpenAPI api = new OpenAPI();
                api.info(new Info()
                                .title("Foro Alura")
                                .version("1.0.0")
                                .description("Resolución del challenge 4: Foro Alura del ProgramaONE de Oracle Next Educacion")
                                .contact(contacto));
                api.components(new Components()
                                .addSecuritySchemes("bearer-key",
                                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                                                .bearerFormat("JWT")));
                return api;
        }

}
