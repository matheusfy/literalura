package io.github.matheusfy.litarelura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(
        @JsonAlias Long id,
        @JsonAlias String title,
        @JsonAlias List<AuthorDTO> authors,
        @JsonAlias List<String> languages,
        @JsonAlias Long download_count) {

    @Override
    public String toString() {
        return "Livro: " +
                "title='" + title +
                " id=" + id +
                ", languages=" + languages +
                ", download_count=" + download_count +
                authors.stream().findFirst().get();
    }

}
