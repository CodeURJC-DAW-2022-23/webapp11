package com.techmarket.app.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

public class Sells {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String Id;

    private String Month;

    private int amount;

}
