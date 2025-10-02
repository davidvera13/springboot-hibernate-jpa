package eu.dreamlabs.hibernatejpa.dao.method02jdbctemplate;

import eu.dreamlabs.hibernatejpa.dao.method02jdbctemplate.AuthorJdbc;
import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"local"})
@DataJpaTest
@ComponentScan("eu.dreamlabs.hibernatejpa.method02jdbctemplate")
// required to compile application for older versions ...
//@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorJdbcTest {
    @Autowired
    private AuthorJdbc authorJdbc;

    @Test
    void createAuthor() {
        AuthorEntity entity = new AuthorEntity();
        entity.setFirstName("John");
        entity.setLastName("Doe");
        AuthorEntity savedAuthor = authorJdbc.createAuthor(entity);
        System.out.println(savedAuthor.getId());
        System.out.println(savedAuthor.getFirstName() + " " + savedAuthor.getLastName());
        assertThat(savedAuthor).isNotNull();
    }

    @Test
    void getAuthorById() {
        AuthorEntity authorEntity = authorJdbc.getById(1L);
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getByIdWithBooks() {
        AuthorEntity authorEntity = authorJdbc.getByIdWithBooks(1L);
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void getAuthorByNamePrepStatement() {
        AuthorEntity authorEntity = authorJdbc.getByName("Craig", "Walls");
        assertThat(authorEntity).isNotNull();
    }

    @Test
    void updateAuthor() {
        AuthorEntity entity = new AuthorEntity();
        entity.setFirstName("Walter");
        entity.setLastName("Skinner");
        AuthorEntity savedAuthor = authorJdbc.createAuthor(entity);
        assertThat(savedAuthor.getFirstName()).isEqualTo("Walter");
        assertThat(savedAuthor.getLastName()).isEqualTo("Skinner");

        savedAuthor.setFirstName("Fox");
        savedAuthor.setLastName("Mulder");

        AuthorEntity updated = authorJdbc.updateAuthor(savedAuthor);
        assertThat(updated.getFirstName()).isEqualTo("Fox");
        assertThat(updated.getLastName()).isEqualTo("Mulder");
    }

    @Test
    void deleteAuthor() {
        AuthorEntity entity = new AuthorEntity();
        entity.setFirstName("John");
        entity.setLastName("Wick");
        AuthorEntity savedAuthor = authorJdbc.createAuthor(entity);
        assertThat(savedAuthor.getFirstName()).isEqualTo("John");
        assertThat(savedAuthor.getLastName()).isEqualTo("Wick");

        authorJdbc.deleteAuthor(savedAuthor.getId());
        assertThrows(
                EmptyResultDataAccessException.class, () ->
                        authorJdbc.getById(savedAuthor.getId()));
    }
}