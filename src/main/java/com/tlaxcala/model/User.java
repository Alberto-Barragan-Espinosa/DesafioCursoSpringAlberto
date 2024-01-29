package com.tlaxcala.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE) - GETTERS, SETTERS, TOSTRINGS
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@NoArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@AllArgsConstructor//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@Entity//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
@Table(name = "user_data")//DECORADOR - ANNOTACION (AGREGA COMPORTAMIENTOS A LA CLASE)
public class User {

    @Id
    @EqualsAndHashCode.Include
    private Integer idUser;

    @Column(nullable = false, length = 60, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    // forma 2: many to many
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", 
        joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "idUser"),
        inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "idRole")
    )
    private List<Role> roles;
}
