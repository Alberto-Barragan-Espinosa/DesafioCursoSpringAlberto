package com.tlaxcala.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tlaxcala.security.JwtRequest;
import com.tlaxcala.security.JwtResponse;
import com.tlaxcala.security.JwtTokenUtil;
import com.tlaxcala.security.JwtUserDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController//categoriza esta clase como un Controlador REST
//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
@RequiredArgsConstructor//define la ruta padre para todos los servicios de la clase - buena practica: la ruta padre siempre sea en plural
public class LoginController {

    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final AuthenticationManager authenticationManager;
    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final JwtTokenUtil jwtTokenUtil;
    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final JwtUserDetailsService userDetailsService;

    @Operation(summary = "Permite la identificacion de un usuario ante el sistema", 
           description= "Al ingresar la informacion de usuario de manera correcta y si esta coincide con la que se encuentra persistida en la base de datos permitirá al usuario acceder al sistema y hacer uso de los privilegios y servicios a los que se le otorgó acceso.")
    @ApiResponse(responseCode = "200", description = "Inicio de sesion autorizado por el JWT")
    @ApiResponse(responseCode = "401", description = "Usuario inexisitente en la base de datos o no autorizado")
    @PostMapping("/login")//Mapeo de servicio REST
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest req) throws Exception {

        try{
            authenticate(req.getUsername(), req.getPassword());

            final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }catch(Exception e){
            System.out.println("ERROR FORZADO");
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
        
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    
}
