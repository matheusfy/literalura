package io.github.matheusfy.litarelura.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

import java.lang.annotation.Documented;


public class Persona {

    @Column(nullable = false)
    @JsonProperty(value = "name") protected  String name;

    @JsonProperty(value = "birth_year") protected Integer birthYear;
    @JsonProperty(value = "death_year") protected Integer deathYear;

    public Persona() {
    }
}
