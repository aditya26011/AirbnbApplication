package com.aditya.pawar.AirbnbApplication.entity;


import com.aditya.pawar.AirbnbApplication.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

//    @ManyToMany(mappedBy = "guests")//Not defining JoinTable here since already defined in Booking
//    private Set<Booking> bookings; no need of this

}
