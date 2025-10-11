package com.aditya.pawar.AirbnbApplication.entity;

import com.aditya.pawar.AirbnbApplication.entity.enums.PayementStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String transcationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayementStatus payementStatus;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal amount;
}
