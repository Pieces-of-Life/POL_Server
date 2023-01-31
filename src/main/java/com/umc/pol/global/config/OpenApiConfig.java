package com.umc.pol.global.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI(
    @Value("${springdoc.version}") String appVersion
  ) {

    return new OpenAPI()
      .info(new Info()
        .title("Pieces-of-Life API")
        .version(appVersion)
        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

  }
}
