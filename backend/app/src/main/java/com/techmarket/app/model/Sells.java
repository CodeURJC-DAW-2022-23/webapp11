package com.techmarket.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sells {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String Id;

    private String Month;

    private int amount;

}
