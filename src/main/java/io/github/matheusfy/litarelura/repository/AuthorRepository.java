package io.github.matheusfy.litarelura.repository;

import io.github.matheusfy.litarelura.model.entity.Author;
import io.github.matheusfy.litarelura.model.entity.dto.AuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String nome);

    // @Query("select a from authors as a where lower(a.name) like %:name%")
    List<Author> findByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM authors as a WHERE (a.birth_year >= :birthYear OR a.death_year >= :birthYear) AND (a.death_year <= :deathYear OR a.birth_year <= :deathYear)", nativeQuery = true)
    List<Author> findAuthorLivedIn(Long birthYear, Long deathYear);
}
