package com.tlaxcala.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
/*
 * CONFIGURACION DEL SWAGGER
 */
@Configuration
public class OpenAPIConfig {
  @Value("${bezkoder.openapi.dev-url}")
  private String devUrl;
  @Value("${bezkoder.openapi.prod-url}")
  private String prodUrl;
  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");
    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");
    Contact contact = new Contact();
    contact.setEmail("mchamorro@laescala.cl");
    contact.setName("Escalab");
    contact.setUrl("https://www.laescala.cl");
    License mitLicense = new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0");
    Info info = new Info()
        .title("Mediapp Api Documentation by Alberto Barragan Espinosa")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints for curse Spring Boot.").termsOfService("https://www.laescala.cl")
        .license(mitLicense);
    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}