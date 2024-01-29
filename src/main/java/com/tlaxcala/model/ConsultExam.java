package com.tlaxcala.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - GETTERS, SETTERS, TOSTRINGS
@NoArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@AllArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@Entity//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@IdClass(ConsultExamPK.class)//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
public class ConsultExam {

    @Id
    private Consult consult;

    @Id
    private Exam exam;

    public ConsultExam(Exam exam) {
        this.exam = exam;
    }
}


