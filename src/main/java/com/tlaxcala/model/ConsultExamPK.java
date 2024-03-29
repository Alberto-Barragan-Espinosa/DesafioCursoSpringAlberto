package com.tlaxcala.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@EqualsAndHashCode//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
public class ConsultExamPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_consult", nullable = false)
    private Consult consult;

    @ManyToOne
    @JoinColumn(name = "id_exam", nullable = false)
    private Exam exam;

    //ConsultExam (C1, EX2)
    //ConsultExam (C2, EX3)
}

// BENIFICIOS VS. FORMA 2
// 1. MAYOR FLEXIBILIDAD DE COMPORTAMIENTO
// 2. VERIFICACIÓN DE IGUAL DE OBJETOS DE INSTANCIAS
