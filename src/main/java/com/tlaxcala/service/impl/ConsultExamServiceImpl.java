package com.tlaxcala.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tlaxcala.model.ConsultExam;

import com.tlaxcala.repo.IConsultExamRepo;

import com.tlaxcala.service.IConsultExamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
public class ConsultExamServiceImpl implements IConsultExamService {

    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final IConsultExamRepo repo;

    @Override
    public List<ConsultExam> getExamsByConsultId(Integer idConsult) {
        return repo.getExamsByConsultId(idConsult);
    }

}