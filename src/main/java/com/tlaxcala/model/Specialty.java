package com.tlaxcala.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - GETTERS, SETTERS, TOSTRINGS
@NoArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@AllArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@Entity//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
public class Specialty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSpecialty;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String description;
}
