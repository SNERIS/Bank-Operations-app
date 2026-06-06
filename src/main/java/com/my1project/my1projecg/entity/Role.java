package com.my1project.my1projecg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter @Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Emër specifik për ID-në
    private Long id;

    @Column(name = "role_name", unique = true, nullable = false, length = 20)
    private String name;
}