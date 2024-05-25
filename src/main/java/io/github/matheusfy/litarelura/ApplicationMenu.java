package io.github.matheusfy.litarelura;

import java.util.Scanner;

public class ApplicationMenu {

    public ApplicationMenu(){

    }

    public void exibeMenu(){
        Scanner cmd = new Scanner(System.in);
        String opcao = "-1";

        String textMenu = """
            ***********************************************************
            Digite uma das opções abaixo:
            1 - Buscar livro pelo titulo
            2 - Listar livros buscados
            3 - Listar livros com base no idioma
            4 - Listar autores dos livros buscados
            5 - Consultar autores vivos no ano: 
            6 - Listar livros por idioma
            7 - [EXTRA] Buscar top 10 livros mais baixados
            8 - [EXTRA] Buscar no banco pelo nome do author
            9 - [EXTRA] Outro tipos de consultas para buscar autores.
            0 - Sair...
            """;
        while (!opcao.equals("0") && !opcao.equals("s")){
            System.out.println(textMenu);
            opcao = cmd.nextLine();
            switch (opcao){
                case "1" -> getBookByTitle();
                case "2" -> getRequestedBooks();
                case "3" -> getRequestedBooksByLanguage();
                case "4" -> getRequestedBooksAuthors();
                case "5" -> getAuthorsLivedIn();
                case "6" -> getBooksByLanguage();
                case "7" -> {}
                case "8" -> {}
                case "9" -> {}
                case "0" -> System.out.println("Saindo ...");
            }
            if(!opcao.equals("0")) {
                System.out.println("\n Deseja sair? S/N");
                opcao = cmd.nextLine().toLowerCase();
            }

        }
    }

    private void getBooksByLanguage() {
        // TODO: ????
    }

    private void getAuthorsLivedIn() {
        // TODO: Buscar autores vividos nos anos X.
    }

    private void getRequestedBooksAuthors() {
      // TODO: Implementar a busca dos autores dos livros.
    }

    private void getRequestedBooksByLanguage() {
        // TODO: Implementar a busca dos livros no banco por linguagem
    }

    private void getRequestedBooks() {
        // TODO: Implementar a busca dos livros no banco
    }

    private void getBookByTitle() {
        // TODO: Implementar a busca do livro pelo titulo
    }
}
