package com.tlaxcala.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlaxcala.dto.ConsultExamDTO;
import com.tlaxcala.model.ConsultExam;
import com.tlaxcala.service.IConsultExamService;

import lombok.RequiredArgsConstructor;

@RestController//categoriza esta clase como un Controlador REST
@RequestMapping("/consultexams")//define la ruta padre para todos los servicios de la clase - buena practica: la ruta padre siempre sea en plural
@RequiredArgsConstructor//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
public class ConsultExamController {

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final IConsultExamService service;//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED

    @GetMapping("/{idConsult}")//Mapeo de servicio REST
    public ResponseEntity<List<ConsultExamDTO>> getConsultsById(@PathVariable("idConsult") Integer idConsult) {
        List<ConsultExam> lst = service.getExamsByConsultId(idConsult);
        List<ConsultExamDTO> lstDTO = mapper.map(lst, new TypeToken<List<ConsultExamDTO>>(){}.getType());

        return new ResponseEntity<>(lstDTO, HttpStatus.OK);
    }
    
}
