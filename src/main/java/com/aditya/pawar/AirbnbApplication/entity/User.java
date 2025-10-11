package com.aditya.pawar.AirbnbApplication.entity;

import com.aditya.pawar.AirbnbApplication.entity.enums.Role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_user")//since postgres doesn't allow to create a table called User
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;//encode

    @Column(nullable = true)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)// will create another table app_user roles
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
