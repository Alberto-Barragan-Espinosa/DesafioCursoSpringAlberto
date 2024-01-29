package com.tlaxcala.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // habilita todas las configs.
@EnableMethodSecurity // Es una especificación para que la seguridad pueda utilizar el método @PreAuthorize
@RequiredArgsConstructor//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
public class WebSecurityConfig { // utiliza todas las configuraciones ya realizadas y las activa en la app

    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final UserDetailsService jwtUserDetailsService;
    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // instancia necesaria para propagar la autenticación de procesos
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // devuelve una instancia de password encriptado
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder()); // contrastar la información del user en cuanto al pass pasado vs lo que hay en bdd
    }

    // filtro que se debe agregar para todas las versiones de spring
    @Bean
    
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable) // deshabilita la protección de ataques para forms incrustados en el backend
            .authorizeHttpRequests(req -> req
                .requestMatchers("/login").permitAll() // ruta permitida para todos los usuarios
                .requestMatchers("/v2/api-docs").permitAll() // ruta permitida para todos los usuarios //Swagger - funcion GET primitiva
                .requestMatchers("/v3/api-docs").permitAll() // ruta permitida para todos los usuarios //Swagger Open API - funcion GET primitiva
                .requestMatchers("/configuration/ui").permitAll() // ruta permitida para todos los usuarios //Swagger
                .requestMatchers("/swagger-resources/**").permitAll() // ruta permitida para todos los usuarios //Swager
                .requestMatchers("/configuration/security").permitAll() // ruta permitida para todos los usuarios //Swager
                .requestMatchers("/webjars/**").permitAll() // ruta permitida para todos los usuarios //Swager - PRUEBAS DE PERMISO NO USADAS
                //.requestMatchers("/swagger-ui/index.html#/admin-controller").permitAll() // ruta permitida para todos los usuarios //Swager - PRUEBAS DE PERMISO NO USADAS
                //.requestMatchers("/swagger-ui/index.html").permitAll() // ruta permitida para todos los usuarios //Swager - PRUEBAS DE PERMISO NO USADAS
                //.requestMatchers("/swagger-ui/swagger-ui-custom.html").permitAll() // ruta permitida para todos los usuarios //Swager - PRUEBAS DE PERMISO NO USADAS
                //.requestMatchers("/swagger-ui.html").permitAll() // ruta permitida para todos los usuarios //Swager - PRUEBAS DE PERMISO NO USADAS
                //.requestMatchers("/swagger-ui/index.html?continue").permitAll() // ruta permitida para todos los usuarios //Swager - PRUEBAS DE PERMISO NO USADAS
                .requestMatchers("/swagger-ui/**").permitAll() // ruta permitida para todos los usuarios //Swagger Open API - INTERFAZ GRAFICA
                .requestMatchers("/v3/api-docs/swagger-config/**").permitAll() // ruta permitida para todos los usuarios //Swagger Open API - RECURSOS DE INTERFAZ GRAFICA
                .requestMatchers("/index").permitAll()
                .requestMatchers("/").permitAll()
                //.requestMatchers("/**").permitAll() // ruta permitida para todos los usuarios // NO USAR - QUITA LA SEGURIDAD DE TODO
                
                //.requestMatchers("/patients/**").permitAll() | .authenticated()
                .anyRequest().authenticated() // cualquier otra ruta de las no definidas le agrega el comportamiento
            )
            .httpBasic(Customizer.withDefaults()) // proteger el contexto http desde el servidor anti ataques http
            .formLogin(AbstractHttpConfigurer::disable) // deshabilito el form basic de spring security
            .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // le damos la facultad a la clase interceptora de excepciones
            .sessionManagement(Customizer.withDefaults()); // para que se disponga de cara al manejo de sesiones

            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // compruebo primero mediante este filtro que viene el token para verificarlo de acuerdo al usuario a autenticar

            return httpSecurity.build(); // retorno el bloque del filtro creado
            //return null;
    }
    
}
