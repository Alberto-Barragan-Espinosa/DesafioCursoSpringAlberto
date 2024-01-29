package com.tlaxcala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableWebMvc//Swagger//no utilizado
//@EnableSwagger2//PERMITIR COMENTARIOS EN SWAGGER
@SecurityScheme(name = "Mediapp", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)//PERMITIR INICIO DE SESION PARA EJECUCION DE PRUEBAS QUE REQUIEREN TOKEN
public class MediappBackend {

    public static void main(String[] args) {
        SpringApplication.run(MediappBackend.class, args);
    }
}
