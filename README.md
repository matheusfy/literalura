![Thumbnail project](./imgs/Literalura.png)
  
# Literalura

Neste projeto desenvolvemos uma aplicação de um Catálogo de Livros que ofereça interação textual (via console) com os usuários, proporcionando no mínimo 5 opções de interação. Os livros serão buscados através de uma API específica conhecida como **Gutendex** onde iremos consultar informações de livros e seus autores.

## 🔨 Funcionalidades do projeto

Para acessar informações sobre as funcionalidades, acesse -> [Functionalities](./functionalities/README.md)

## ✔️ Técnicas e tecnologias utilizadas

- `Maven`
- `Java 17`
- `Spring without web`
- `Gugantex API`
- `Postgresql`
- `Dependências:`
  - `Jackson`: core, annotations, databind. Version 2.17.1
  - `JPA/Hibernate`
  - `Spring-start`



## 🛠️ Abrir e rodar o projeto

Inicialmente escolha uma pasta local e faça um fork e depois clone seu repositório:


```markdown
git clone git@github.com:<user>/literalura.git 
```

### Intellij

1. Abra a configuração da aplicação e adicione as variáveis abaixo alterado de acordo com a sua aplicação:
```
DB_HOST=<host>;DB_PORT=<port>;DB_NAME=<nome_bancodedados>;DB_USER=<nome_user>;DB_PWD=<senha>;
```
2. Adicione a variável do caminho que iremos consultar:
```
API_URL=https://gutendex.com/books
```
3. Caso necessário, verifique o tipo de codificação em **Settings > Editor > File Encodings**



## 📚 Mais informações do curso

Desafio oferecido pela Alura + One Oracle Next Education: [Link](https://cursos.alura.com.br/course/spring-boot-challenge-literalura)
