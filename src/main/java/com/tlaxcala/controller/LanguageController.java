package com.tlaxcala.controller;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController//categoriza esta clase como un Controlador REST
@RequestMapping("/languages")//define la ruta padre para todos los servicios de la clase - buena practica: la ruta padre siempre sea en plural
@RequiredArgsConstructor//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
public class LanguageController {

    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final LocaleResolver localeResolver; // para resolver los locations
    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final HttpServletRequest httpServletRequest; // representa el request
    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final HttpServletResponse httpServletResponse; // representa el response

    @GetMapping("/locale/{loc}")//Mapeo de servicio REST
    public ResponseEntity<Void> changeLocale(@PathVariable("loc") String loc) {
        Locale userLocale = switch (loc) {
            case "en" -> Locale.ENGLISH;
            case "fr" -> Locale.FRANCE;
            default -> Locale.ROOT;
        };
        localeResolver.setLocale(httpServletRequest, httpServletResponse, userLocale);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
