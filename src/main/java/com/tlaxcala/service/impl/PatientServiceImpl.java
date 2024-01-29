package com.tlaxcala.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tlaxcala.model.Patient;
import com.tlaxcala.repo.IGenericRepo;
import com.tlaxcala.repo.IPatientRepo;
import com.tlaxcala.service.IPatientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
public class PatientServiceImpl extends CRUDImpl<Patient, Integer> implements IPatientService {

    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final IPatientRepo repo;

    @Override
    protected IGenericRepo<Patient, Integer> getRepo() {
        return repo;
    }

    @Override
    public Page<Patient> listPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

}
