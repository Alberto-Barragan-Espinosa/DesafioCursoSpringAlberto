package com.tlaxcala.model;

import jakarta.persistence.*;
import lombok.*;

@Data//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - GETTERS, SETTERS, TOSTRINGS
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@NoArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@AllArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@Entity//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
public class Role {

    @Id
    @EqualsAndHashCode.Include
    private Integer idRole;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String description;
}
