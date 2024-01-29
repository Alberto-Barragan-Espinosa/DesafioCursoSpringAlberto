package com.tlaxcala.service.impl;

import org.springframework.stereotype.Service;

import com.tlaxcala.model.MediaFile;
import com.tlaxcala.repo.IGenericRepo;
import com.tlaxcala.repo.IMediaFileRepo;
import com.tlaxcala.service.IMediaFileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
public class MediaFileServiceImpl extends CRUDImpl<MediaFile, Integer> implements IMediaFileService {

    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final IMediaFileRepo repo;

    @Override
    protected IGenericRepo<MediaFile, Integer> getRepo() {
        return repo;
    }
    
}
