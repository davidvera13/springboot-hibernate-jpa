package eu.dreamlabs.hibernatejpa.dao;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles({"local"})
@DataJpaTest
@ComponentScan("eu.dreamlabs.hibernatejpa.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoTest {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao authorDao;

    @Test
    void createBook() {
        BookEntity entity = new BookEntity();
        entity.setTitle("author1");
        entity.setIsbn("123456789");
        entity.setPublisher("publisher");
        AuthorEntity author = new AuthorEntity();
        author.setId(3L);
        entity.setAuthor(author);
        BookEntity saved = bookDao.createBook(entity);
        assertThat(saved).isNotNull();
    }

    @Test
    void getAuthorById() {
        BookEntity book = bookDao.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void getAuthorByNamePrepStatement() {
        BookEntity authorEntity = bookDao.getByTitle("Spring in Action, 5th Edition");
        assertThat(authorEntity).isNotNull();
    }


    @Test
    void updateAuthor() {
        BookEntity entity = new BookEntity();
        entity.setTitle("book2");
        entity.setIsbn("123456788");
        entity.setPublisher("publisher2");
        AuthorEntity author = new AuthorEntity();
        author.setId(3L);
        entity.setAuthor(author);
        BookEntity saved = bookDao.createBook(entity);
        assertThat(saved.getTitle()).isEqualTo("book2");
        assertThat(saved.getIsbn()).isEqualTo("123456788");
        assertThat(saved.getPublisher()).isEqualTo("publisher2");

        saved.setTitle("book2b");
        saved.setIsbn("123456780");
        saved.setPublisher("PUBLISHER2b");

        BookEntity updated = bookDao.updateBook(saved);
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

        BookEntity saved = bookDao.createBook(entity);
        assertThat(saved.getTitle()).isEqualTo("book3");
        assertThat(saved.getIsbn()).isEqualTo("123456777");

        bookDao.deleteBookById(saved.getId());
        assertThat(bookDao.getById(saved.getId())).isNull();
    }

}