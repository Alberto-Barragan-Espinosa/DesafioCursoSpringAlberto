package com.tlaxcala.controller;

import com.tlaxcala.dto.MenuDTO;
import com.tlaxcala.model.Menu;
import com.tlaxcala.service.IMenuService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController//categoriza esta clase como un Controlador REST
@RequestMapping("/menus")//define la ruta padre para todos los servicios de la clase - buena practica: la ruta padre siempre sea en plural
@RequiredArgsConstructor//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
public class MenuController {

    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final IMenuService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @PostMapping("/user")//Mapeo de servicio REST
    public ResponseEntity<List<MenuDTO>> getMenusByUser(@RequestBody String username) throws Exception {
        List<Menu> menus = service.getMenusByUsername(username);
        List<MenuDTO> menusDTO = menus.stream().map(e -> modelMapper.map(e, MenuDTO.class)).toList();

        return new ResponseEntity<>(menusDTO, HttpStatus.OK);
    }
}
