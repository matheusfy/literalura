package io.github.matheusfy.litarelura.model.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty (value = "title")
    private String title;

    @ElementCollection
    @JsonProperty(value = "languages")
    private List<String> languages;

    @JsonProperty(value = "download_count")
    private Long downloadCount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonProperty(value = "authors")
    private Author author;
    

    public Book() {
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public void addLanguages(String language){
        this.languages.add(language);
    }

}
