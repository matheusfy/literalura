package io.github.matheusfy.litarelura;

import io.github.matheusfy.litarelura.connections.BooksApi;
import io.github.matheusfy.litarelura.connections.exception.ConnectionTimeoutException;
import io.github.matheusfy.litarelura.mapper.JsonConverter;
import io.github.matheusfy.litarelura.mapper.exception.JsonMappingFailException;
import io.github.matheusfy.litarelura.model.dto.BookDTO;
import io.github.matheusfy.litarelura.model.entity.Author;
import io.github.matheusfy.litarelura.model.entity.Book;
import io.github.matheusfy.litarelura.repository.AuthorRepository;
import io.github.matheusfy.litarelura.repository.BookRepository;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ApplicationMenu {

    private final BooksApi service = new BooksApi();
    private final Scanner cmd = new Scanner(System.in);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final JsonConverter jsonConverterService = new JsonConverter();

    public ApplicationMenu(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void exibeMenu() {
        Scanner cmd = new Scanner(System.in);
        String opcao = "-1";

        String textMenu = """
                ***********************************************************
                Digite uma das opções abaixo:
                1 - Buscar livro pelo titulo.
                2 - Listar todos livros buscados.
                3 - Listar livros com base no idioma.
                4 - Listar autores.
                5 - Consultar autores vivos no ano:
                6 - Buscar a quantidade de livros registrado pelo idioma informado.
                7 - [EXTRA] Buscar no banco pelo nome do author.
                8 - [EXTRA] Buscar top 10 livros mais baixados
                9 - [EXTRA] Busca pelo autor na api.
                0 - Sair...
                """;

        // 97 - [TESTE] Atualiza id dos livros que estao vazios
        // 98 - [TESTE] salvar uma lista de livros e autores a partir de um Json local
        // 99 - [TESTE] faz a busca de um autor pelo nome.
        // 100 - [TESTE] atualiza banco a partir de um json local de livros

        while (!opcao.equals("0") && !opcao.equals("s")) {
            System.out.println(textMenu);
            opcao = cmd.nextLine();
            switch (opcao) {
                case "1" -> getBooksFromApi(); // feito
                case "2" -> getAllBooks(); // feito
                case "3" -> getBooksByLanguage(); // feito
                case "4" -> getAllAuthors(); // feito
                case "5" -> getAuthorsLivedIn(); // feito
                case "6" -> getBookOnLanguage(); // feito
                case "7" -> getBooksByAuthors();
                case "8" -> getTop10DownloadedBooks();
                case "9" -> getBooksByAuthorFromApi();

                // *************** Cases de teste ************** //
                // case "97" -> updateBooks();
                // case "98" -> testeSaving();
                // case "99" -> tryGetAutor();
                // case "100" -> updateBooksLocally();

                case "0" -> System.out.println("Saindo ...");
            }
            if (!opcao.equals("0")) {
                System.out.println("\n Deseja sair? S/N");
                opcao = cmd.nextLine().toLowerCase();
            }
        }
        cmd.close();
    }

    private void getBooksByAuthorFromApi() {
        System.out.println("Digite o nome do author que deseja buscar os livros na api. ");
        String author = cmd.nextLine().replace(" ", "+");

        try {
            String resultJson = service.getSearch(author);
            List<BookDTO> booksDTOs = jsonConverterService.json2Books(resultJson);

            if (!booksDTOs.isEmpty()) {
                List<Book> books = booksDTOs.stream().map(Book::new).toList();

                books.forEach(System.out::println);

                trySaveBooks(books);
            } else {
                System.out.println("Não encontramos livros para o autor: " + author);
            }
        } catch (Exception error) {
            System.out.println("[Exception] reason:" + error.getMessage());
        }

    }

    private void getBookOnLanguage() {
        System.out.println("Digite a sigla do idioma que gostaria de consultar: ");
        String language = cmd.nextLine();
        Long bookQty = bookRepository.countByLanguages(language);
        System.out.println(bookQty + " Livros encontrado no idioma: " + language);
    }

    private void getAllAuthors() {
        List<Author> authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        if (!authors.isEmpty()) {
            System.out.println("Autores encontrados: ");
            authors.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor encontrado no banco de dados.");
        }
    }

    private void getTop10DownloadedBooks() {
        List<Book> top10Books = bookRepository.findTop10ByDownloadCount();
        if (!top10Books.isEmpty()) {
            top10Books.forEach(book -> System.out.println("Baixados: " + book.getDownloadCount() + " - Titulo: "
                    + book.getTitle() + " - Autor: " + book.getAuthor().getName()));
        }
    }

    // private void tryGetAutor() {
    // System.out.println("Digite um nome de autor:");
    // String nome = cmd.nextLine();
    // Optional<Author> author = authorRepository.findByName(nome);
    // if (author.isPresent()) {
    // System.out.println(author.get());
    // } else {
    // System.out.println("Autor nao encontrado");
    // }
    // }

    private void getBooksFromApi() {
        System.out.println("Qual o nome do livro/autor que deseja buscar? ");
        String bookName = cmd.nextLine().replace(" ", "+");

        try {
            List<Book> books = convertBookJson2List(searchOnWeb(bookName));

            if (!books.isEmpty()) {
                trySaveBooks(books);
            } else {
                System.out.println("Nenhum livro encontrado com o nome: " + bookName);
            }

        } catch (JsonMappingFailException error) {
            System.out.println(error.getMessage());
        } catch (ConnectionTimeoutException timeout) {
            System.out.println(timeout.getMessage());
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro nao mapeado: " + e.getMessage());
        }

    }

    private void getBooksByLanguage() {

        List<String> languages = bookRepository.findLanguages();
        System.out.println("Estes são os idiomas disponiveis: ");
        languages.forEach(System.out::println);

        System.out.println("Digite livros de um idioma especifico: ");
        String language = cmd.nextLine();
        List<Book> books = bookRepository.findByLanguage(language);

        if (!books.isEmpty()) {
            System.out.println("Livros encontrados: ");
            books.forEach(System.out::println);
        } else {
            System.out.println("Nenhum livro encontrado.");
        }
    }

    private void getAuthorsLivedIn() {
        System.out.println("Escolha um intervalo de vida que o autor viveu. ");
        try {
            System.out.println("Inicio:");
            Long startYear = cmd.nextLong();
            cmd.nextLine();
            System.out.println("Fim:");
            Long endYear = cmd.nextLong();
            cmd.nextLine();
            if (startYear < endYear) {
                System.out.println("Iniciando a pesquisa");
                List<Author> authors = authorRepository.findAuthorLivedIn(startYear, endYear);
                if (!authors.isEmpty()) {
                    authors.forEach(System.out::println);
                } else {
                    System.out
                            .println("Nenhum autor encontrado com o intervalo de vida: " + startYear + " - " + endYear);
                }
            } else {
                System.out.println("Ano de inicio não pode ser maior que o ano final.");
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    private void getBooksByAuthors() {
        System.out.println("Digite o nome de um autor que deseja buscar os livro:");
        String authorName = cmd.nextLine();

        List<Author> authors = authorRepository.findByNameContainingIgnoreCase(authorName);
        if (!authors.isEmpty()) {
            if (authors.size() == 1) {
                printAuthorsBooks(authors.get(0));
            } else {
                try {
                    Author author = chooseValidAuthorFromList(authors);
                    List<Book> books = bookRepository.findByAuthor(author);
                    System.out.println("Livros do autor: " + author.getName());
                    books.forEach(System.out::println);
                } catch (Exception error) {
                    System.out.println(error.getMessage());
                }
            }
        } else {
            System.out.println("Nenhum autor encontrado com o nome: " + authorName);
        }
    }

    private Author chooseValidAuthorFromList(List<Author> authors) {
        System.out.println("Foram encontrados diversos autores com este nome. O desejado é algum desta listas?");
        int i = 0;

        for (Author author : authors) {
            System.out.println(i + " - " + author.getName());
            i++;
        }

        System.out.println("Se sim digite o numero do autor desejado, se não digite -1: ");
        int index = cmd.nextInt();
        cmd.nextLine();

        return authors.get(index - 1);
    }

    private void printAuthorsBooks(Author author) {
        System.out.println("Autor encontrado: " + author.getName());
        List<Book> books = bookRepository.findByAuthor(author);
        System.out.println("Obras encontradas: ");
        books.forEach(System.out::println);
    }

    private void getAllBooks() {
        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);
    }

    // private void testeSaving() {
    // try {
    // List<BookDTO> booksDTOs = readBooksFromJson();
    // if (booksDTOs != null) {
    // List<Book> books = booksDTOs.stream().map(Book::new).toList();
    // trySaveBooks(books);
    // }
    // } catch (IOException e) {
    // System.out.println("Deu pau na conversao");
    // }
    // }

    // private void updateBooks(){
    //
    // List<Book> emptyLibIdBooks = bookRepository.findByLibIdNull();
    // for(Book emptyLibIdBook : emptyLibIdBooks){
    // try{
    // // Busca o livro na web pelo nome
    // List<BookDTO> bookDTOS =
    // jsonConverterService.json2Books(searchOnWeb(emptyLibIdBook.getTitle().replace("
    // ", "+")));
    //
    // if(bookDTOS.size() == 1){
    // System.out.println("Libid: " + bookDTOS.get(0).id() + " Nome: " +
    // bookDTOS.get(0).title());
    // emptyLibIdBook.setLibId(bookDTOS.get(0).id());
    // bookRepository.save(emptyLibIdBook);
    // } else {
    // System.out.println("encontrado mais de 1 livro com este nome: " +
    // emptyLibIdBook.getTitle());
    // }
    // } catch (Exception error){
    // System.out.println("[Exception] reason:" + error.getMessage());
    // }
    // }
    // }

    // Funcao utilizado para teste = Carrega um json local e busca no banco o livro
    // com aquele titulo se o livro nao tiver lib_id, setamos ele com o que temos no
    // json
    // private void updateBooksLocally(){
    // List<BookDTO> bookDTOS =
    // jsonConverterService.json2Books(searchOnWeb("Almeida"));
    // for (BookDTO bookDTO : bookDTOS){
    // Optional<Book> opBookFromDB = bookRepository.findByTitle(bookDTO.title());
    // if(opBookFromDB.isPresent()){
    // Book bookFromDB = opBookFromDB.get();
    // if (bookFromDB.getLibId() == null)
    // {
    // System.out.println("Id do livro " + bookFromDB.getTitle() + " é null. Setando
    // ele com o valor novo de : " + bookDTO.id());
    // bookFromDB.setLibId(bookDTO.id());
    // System.out.println("O objeto do livro com o novo id: " + bookFromDB);
    //
    // bookRepository.save(bookFromDB);
    // }
    // }
    // }
    // }

    // private List<BookDTO> readBooksFromJson() throws IOException {
    // JsonConverter jsonConverter = new JsonConverter();
    // String json = new ObjectMapper().readTree(new File("book.json")).toString();
    // return jsonConverter.json2Books(json);
    // }

    private List<Book> booksDTO2Book(List<BookDTO> booksDTO) {
        return booksDTO.stream()
                .map(Book::new)
                .collect(Collectors.toList());
    }

    private String searchOnWeb(String bookName) throws IOException, InterruptedException {
        return service.getSearch(bookName);
    }

    private List<Book> convertBookJson2List(String responseJson) throws JsonMappingFailException {
        List<BookDTO> booksDTO = jsonConverterService.json2Books(responseJson);
        return booksDTO2Book(booksDTO);
    }

    public void trySaveBooks(List<Book> books) {

        for (Book book : books) {
            try {
                Author author = book.getAuthor();
                Optional<Author> authorBuscado = authorRepository.findByName(author.getName());
                if (authorBuscado.isPresent()) {
                    author = authorRepository.saveAndFlush(authorBuscado.get());
                } else {
                    author = authorRepository.save(author);
                }
                System.out.println(book);
                book.setAuthor(author);
                authorRepository.save(author);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Erro ao salvar livro: " + book.getTitle() + " - " + "chave duplicada no banco.");
            } catch (IllegalArgumentException argError) {
                System.out.println("Argumento invalido foi passado: " + " - " + argError.getMessage());
            }
        }
    }

}
