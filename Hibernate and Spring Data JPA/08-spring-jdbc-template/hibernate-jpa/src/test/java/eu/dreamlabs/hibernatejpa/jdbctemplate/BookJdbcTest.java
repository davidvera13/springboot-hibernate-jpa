package eu.dreamlabs.hibernatejpa.jdbctemplate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"local"})
@DataJpaTest
@ComponentScan("eu.dreamlabs.hibernatejpa.jdbctemplate")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJdbcTest {
    @Autowired
    private AuthorJdbc authorJdbc;
    
    @Autowired
    private BookJdbc bookJdbc;


    @Test
    void createBook() {
        BookEntity entity = new BookEntity();
        entity.setTitle("author1");
        entity.setIsbn("123456789");
        entity.setPublisher("publisher");
        AuthorEntity author = new AuthorEntity();
        author.setId(3L);
        entity.setAuthor(author);
        BookEntity saved = bookJdbc.createBook(entity);
        assertThat(saved).isNotNull();
    }

    @Test
    void getBookById() {
        BookEntity book = bookJdbc.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void getBookByNamePrepStatement() {
        BookEntity authorEntity = bookJdbc.getByTitle("Spring in Action, 5th Edition");
        assertThat(authorEntity).isNotNull();
    }


    @Test
    void updateBook() {
        BookEntity entity = new BookEntity();
        entity.setTitle("book2");
        entity.setIsbn("123456788");
        entity.setPublisher("publisher2");
        AuthorEntity author = new AuthorEntity();
        author.setId(3L);
        entity.setAuthor(author);
        BookEntity saved = bookJdbc.createBook(entity);
        assertThat(saved.getTitle()).isEqualTo("book2");
        assertThat(saved.getIsbn()).isEqualTo("123456788");
        assertThat(saved.getPublisher()).isEqualTo("publisher2");

        saved.setTitle("book2b");
        saved.setIsbn("123456780");
        saved.setPublisher("PUBLISHER2b");

        BookEntity updated = bookJdbc.updateBook(saved);
        assertThat(updated.getTitle()).isEqualTo("book2b");
    }

    @Test
    void deleteBook() {
        BookEntity entity = new BookEntity();
        entity.setTitle("book3");
        entity.setIsbn("123456777");
        entity.setPublisher("publisher3");
        AuthorEntity author = new AuthorEntity();
        author.setId(3L);
        entity.setAuthor(author);

        BookEntity saved = bookJdbc.createBook(entity);
        assertThat(saved.getTitle()).isEqualTo("book3");
        assertThat(saved.getIsbn()).isEqualTo("123456777");

        bookJdbc.deleteBookById(saved.getId());
        assertThat(bookJdbc.getById(saved.getId())).isNull();
    }

}