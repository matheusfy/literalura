package io.github.matheusfy.litarelura;

import io.github.matheusfy.litarelura.repository.AuthorRepository;
import io.github.matheusfy.litarelura.repository.BookRepository;

import java.nio.charset.Charset;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LitareluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LitareluraApplication.class, args);
	}

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public void run(String... args) throws Exception {

		ApplicationMenu menu = new ApplicationMenu(bookRepository, authorRepository);
		menu.exibeMenu();

	}
}
