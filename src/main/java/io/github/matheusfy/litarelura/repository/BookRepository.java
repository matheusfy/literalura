package io.github.matheusfy.litarelura.repository;

import io.github.matheusfy.litarelura.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


}
