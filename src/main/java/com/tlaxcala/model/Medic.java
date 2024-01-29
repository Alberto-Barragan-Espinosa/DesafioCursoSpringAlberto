package com.tlaxcala.model;

import jakarta.persistence.*;
import lombok.*;

@Data//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - GETTERS, SETTERS, TOSTRINGS
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@NoArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@AllArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@Entity//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
public class Medic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMedic;

    @Column(nullable = false, length = 70)
    private String firstName;

    @Column(nullable = false, length = 70)
    private String lastName;

    @Column(nullable = false, length = 12)
    private String codMed;

    private String photoUrl;
}
