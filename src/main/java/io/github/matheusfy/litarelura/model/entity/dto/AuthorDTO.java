package io.github.matheusfy.litarelura.model.entity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDTO(
    @JsonAlias String name,
    @JsonAlias Long birth_year,
    @JsonAlias Long death_year
) {
    @Override
    public String toString() {
        return "Author: " +
            "name='" + name + '\'' +
            ", birth_year=" + birth_year +
            ", death_year=" + death_year;
    }
}
