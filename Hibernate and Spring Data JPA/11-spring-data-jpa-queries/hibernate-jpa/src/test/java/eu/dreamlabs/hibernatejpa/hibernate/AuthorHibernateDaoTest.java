package eu.dreamlabs.hibernatejpa.hibernate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"local"})
@DataJpaTest
@ComponentScan("eu.dreamlabs.hibernatejpa.hibernate")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorHibernateDaoTest {
    @Autowired
    AuthorHibernateDao authorDao;
    
    @Test
    void createAuthor() {
        AuthorEntity entity = new AuthorEntity();
        entity.setFirstName("John");
        entity.setLastName("Doe");
        AuthorEntity savedAuthor = authorDao.createAuthor(entity);
        System.out.println(savedAuthor.getId());
        System.out.println(savedAuthor.getFirstName() + " " + savedAuthor.getLastName());
        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }

    @Test
    void findAll() {
        List<AuthorEntity> authors = authorDao.findAll();
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void getAuthorById() {
        AuthorEntity authorEntity = authorDao.getById(1L);
        assertThat(authorEntity).isNotNull();
    }


    @Test
    void getAuthorByName() {
        AuthorEntity authorEntity = authorDao.getByName("Craig", "Walls");
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getByNameNamedQuery() {
        AuthorEntity authorEntity = authorDao.getByNameNamedQuery("Craig", "Walls");
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getByNameCriteria() {
        AuthorEntity authorEntity = authorDao.getByNameCriteria("Craig", "Walls");
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getByNameNativeQuery() {
        AuthorEntity authorEntity = authorDao.getByNameNativeQuery("Craig", "Walls");
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void listAuthorByLastNameLike() {
        List<AuthorEntity> authors = authorDao.listAuthorByLastNameLike("Wall");
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void updateAuthor() {
        AuthorEntity entity = new AuthorEntity();
        entity.setFirstName("Walter");
        entity.setLastName("Skinner");
        AuthorEntity savedAuthor = authorDao.createAuthor(entity);
        assertThat(savedAuthor.getFirstName()).isEqualTo("Walter");
        assertThat(savedAuthor.getLastName()).isEqualTo("Skinner");

        savedAuthor.setFirstName("Fox");
        savedAuthor.setLastName("Mulder");

        AuthorEntity updated = authorDao.updateAuthor(savedAuthor);
        assertThat(updated.getFirstName()).isEqualTo("Fox");
        assertThat(updated.getLastName()).isEqualTo("Mulder");
    }

    @Test
    void deleteAuthor() {
        AuthorEntity entity = new AuthorEntity();
        entity.setFirstName("John");
        entity.setLastName("Wick");
        AuthorEntity savedAuthor = authorDao.createAuthor(entity);
        assertThat(savedAuthor.getFirstName()).isEqualTo("John");
        assertThat(savedAuthor.getLastName()).isEqualTo("Wick");

        authorDao.deleteAuthor(savedAuthor.getId());
        AuthorEntity deleted = authorDao.getById(savedAuthor.getId());
        assertThat(deleted).isNull();
        //assertThrows(
        //        EmptyResultDataAccessException.class, () ->
        //                authorDao.getById(savedAuthor.getId()));

    }
}