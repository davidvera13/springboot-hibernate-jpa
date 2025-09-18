package eu.dreamlabs.hibernatejpa.dao;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.atIndex;

@ActiveProfiles({"local"})
@DataJpaTest
@ComponentScan("eu.dreamlabs.hibernatejpa.dao")
// required to compile application for older versions ...
//@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoTest {
    @Autowired
    private AuthorDao authorDao;

    @Test
    void createAuthor() {
        AuthorEntity entity = new AuthorEntity();
        entity.setFirstName("John");
        entity.setLastName("Doe");
        AuthorEntity savedAuthor = authorDao.createAuthor(entity);
        assertThat(savedAuthor).isNotNull();
    }

    @Test
    void getAuthorById() {
        AuthorEntity authorEntity = authorDao.getById(1L);
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getAuthorByIdPrepStatement() {
        AuthorEntity authorEntity = authorDao.getByIdPrepStatement(1L);
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getAuthorByNamePrepStatement() {
        AuthorEntity authorEntity = authorDao.getByName("Craig", "Walls");
        assertThat(authorEntity).isNotNull();
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
        assertThat(authorDao.getById(savedAuthor.getId())).isNull();
    }

}