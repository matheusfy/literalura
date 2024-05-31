package io.github.matheusfy.litarelura.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.matheusfy.litarelura.model.dto.BookDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "books")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = true)
    private Long libId;

    @Column(nullable = false, unique = true)
    @JsonProperty(value = "title")
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(value = "languages")
    private List<String> languages;

    @JsonProperty(value = "download_count")
    private Long downloadCount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Author author;

    public Book() {
    }

    public Book(BookDTO book) {
        this.libId = book.id();
        this.title = book.title();
        this.languages = book.languages();
        this.downloadCount = book.download_count();
        this.author = book.authors().stream().findFirst().map(Author::new).get();
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public void addLanguages(String language) {
        this.languages.add(language);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        author.addBook(this);
        this.author = author;
    }

    @Override
    public String toString() {
        return "lib_id=" + libId +
                ", title='" + title + '\'' +
                ", languages=" + languages +
                ", downloadCount=" + downloadCount +
                ", author=" + author;
    }

    public Long getLibId() {
        return libId;
    }

    public void setLibId(Long libId) {
        this.libId = libId;
    }
}
