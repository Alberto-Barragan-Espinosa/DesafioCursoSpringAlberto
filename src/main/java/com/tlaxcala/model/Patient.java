package com.tlaxcala.model;
import jakarta.persistence.*;
import lombok.*;

@Data //DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - GETTERS, SETTERS, TOSTRINGS
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - VALIDADOR DE INSTANCIAS DE OBJETOS
@NoArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - CONSTRUCTOR SIN ARGUMENTOS
@AllArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - CONSTRUCTOR CON ARGUMENTOS
@Entity//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idPatient;

    @Column(length = 70, nullable = false)
    private String firstName;

    @Column(length = 70, nullable = false)
    private String lastName;

    @Column(length = 8, nullable = false)
    private String dni;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 9, nullable = false)
    private String phone;

    @Column(length = 55, nullable = false)
    private String email;
 
}
