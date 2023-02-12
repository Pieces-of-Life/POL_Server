package com.umc.pol.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI(
    @Value("${springdoc.version}") String appVersion
  ) {

    Info info = new Info()
      .title("Pieces-of-Life API")
      .version(appVersion)
      .license(new License().name("Apache 2.0").url("http://springdoc.org"));

    SecurityScheme basicAuth = new SecurityScheme()
      .type(Type.HTTP)
      .scheme("Bearer")
      .bearerFormat("JWT")
      .in(In.HEADER)
      .name("Authorization");

    SecurityRequirement securityItem = new SecurityRequirement().addList("basicAuth");

    return new OpenAPI()
      .components(new Components().addSecuritySchemes("basicAuth", basicAuth))
      .addSecurityItem(securityItem)
      .info(info);
  }
}
