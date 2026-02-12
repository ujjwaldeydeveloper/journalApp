 package com.example.journalApp.config;

 import io.swagger.v3.oas.models.Components;
 import io.swagger.v3.oas.models.OpenAPI;
 import io.swagger.v3.oas.models.info.Info;
 import io.swagger.v3.oas.models.security.SecurityRequirement;
 import io.swagger.v3.oas.models.security.SecurityScheme;
 import io.swagger.v3.oas.models.servers.Server;
 import io.swagger.v3.oas.models.tags.Tag;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;

 import java.util.Arrays;
 import java.util.List;

 @Configuration
 public class SwaggerConfig {

     @Bean
     public OpenAPI myCustomOpenAPI() {
         return new OpenAPI()
                 .info(new Info()
                         .title("Journal App API")
                         .version("1.0")
                         .description("End-to-end encrypted journal application API"))
                 .servers(List.of(new Server().url("http://localhost:7070").description("local"),
                         new Server().url("Not Deployed").description("live")))
                 .tags(Arrays.asList(
                         new Tag().name("Public APIs"),
                         new Tag().name("User APIs"),
                         new Tag().name("Journal APIs"),
                         new Tag().name("Admin APIs")
                 ))
                 .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                 .components(new Components().addSecuritySchemes(
                         "bearerAuth",
                         new SecurityScheme()
                                 .type(SecurityScheme.Type.HTTP)
                                 .scheme("bearer")
                                 .bearerFormat("JWT")
                                 .in(SecurityScheme.In.HEADER)
                                 .name("Authorization")
                 ));
     }
 }
