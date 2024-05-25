package io.github.matheusfy.litarelura.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "authors")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author extends Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public Author() {
    }

    @Override
    public String toString() {
        return "Author: " +
            "id=" + id +
            ", name='" + name + '\'' +
            ", birthYear=" + birthYear +
            ", deathYear=" + deathYear;
    }

}
