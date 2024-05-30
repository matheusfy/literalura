package io.github.matheusfy.litarelura.repository;

import io.github.matheusfy.litarelura.model.entity.Author;
import io.github.matheusfy.litarelura.model.entity.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

	 @Query(value = "select b.* from books as b join book_languages as bl on b.id = bl.book_id where bl.languages = :language", nativeQuery = true)
	 List<Book> findByLanguage(String language);

	List<Book> findByAuthor(Author author);

	@Query(value = "select distinct (bl.languages) from books as b join book_languages as bl on b.id = bl.book_id", nativeQuery = true)
	List<String> findLanguages();

	@Query(value = "select * from books order by download_count desc limit 10", nativeQuery = true)
	List<Book> findTop10ByDownloadCount();

	Long countByLanguages(String language);

	List<Book> findByLibIdNull();

	Optional<Book> findByTitle(String title);
}
