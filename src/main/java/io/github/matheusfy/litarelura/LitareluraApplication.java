package io.github.matheusfy.litarelura;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.matheusfy.litarelura.connections.BooksApi;
import io.github.matheusfy.litarelura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

@SpringBootApplication
public class LitareluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LitareluraApplication.class, args);
	}

	@Autowired
	private BookRepository bookRepository;

	@Override
	public void run(String... args) throws Exception {

		ApplicationMenu menu = new ApplicationMenu();
		menu.exibeMenu();

		// ***************** testes ***************
//		BooksApi service = new BooksApi();
//		service.jsonToBook();
//		service.getSearch("machado+de+assis");
//		service.getSearch("Dom+casmurro");
	}
}
