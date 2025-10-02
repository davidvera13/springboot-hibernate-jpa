package eu.dreamlabs.hibernatejpa.dao.method03hibernate;

import eu.dreamlabs.hibernatejpa.dao.method03hibernate.AuthorHibernateDao;
import eu.dreamlabs.hibernatejpa.dao.method03hibernate.BookHibernateDao;
import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles({"local"})
@DataJpaTest
@ComponentScan("eu.dreamlabs.hibernatejpa.method03hibernate")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookHibernateDaoTest {
    @Autowired
    private BookHibernateDao bookDao;
    @Autowired
    private AuthorHibernateDao authorDao;

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
    void findAll() {
        List<BookEntity> books = bookDao.findAll();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    void getById() {
        BookEntity book = bookDao.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void findByISBN() {
        BookEntity book = bookDao.findByISBN("978-1617294945");
        assertThat(book).isNotNull();
    }
    
    @Test
    void getByTitle() {
        BookEntity authorEntity = bookDao.getByTitle("Spring in Action, 5th Edition");
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void findByTitleNamedQuery() {
        BookEntity authorEntity = bookDao.findByTitleNamedQuery("Spring in Action, 5th Edition");
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getByTitleCriteria() {
        BookEntity authorEntity = bookDao.getByTitleCriteria("Spring in Action, 5th Edition");
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getByTitleNativeQuery() {
        BookEntity authorEntity = bookDao.getByTitleNativeQuery("Spring in Action, 5th Edition");
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