package eu.dreamlabs.hibernatejpa;

import eu.dreamlabs.hibernatejpa.entity.AuthorsUuidEntity;
import eu.dreamlabs.hibernatejpa.entity.BooksNaturalEntity;
import eu.dreamlabs.hibernatejpa.entity.BooksUuidEntity;
import eu.dreamlabs.hibernatejpa.entity.composites.AuthorsCompositeEntity;
import eu.dreamlabs.hibernatejpa.entity.composites.AuthorsEmbeddedEntity;
import eu.dreamlabs.hibernatejpa.entity.composites.NameId;
import eu.dreamlabs.hibernatejpa.repository.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles({"local"})
// we don't want spring to autoconfigure database and use h2, we want db to be configured based on profile with yaml file
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"eu.dreamlabs.hibernatejpa.bootstrap"})
public class MySQLIntegrationTest {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    @Autowired
    AuthorEmbeddedRepository authorEmbeddedRepository;


    // default behaviour is to roll back the transaction ...
    // be sure we're talking to
    @Test
    @Order(1)
    void mysqlTest() {
        long countBefore = bookRepository.count();
        // Error won't be triggered this time
        // note: if we run all test, we have 3 ...
        assertThat(countBefore).isEqualTo(3);
    }

    @Test
    void testBookUuid() {
        BooksUuidEntity bookUuid = bookUuidRepository.save(new BooksUuidEntity());
        assertThat(bookUuid).isNotNull();
        assertThat(bookUuid.getId());

        BooksUuidEntity fetched = bookUuidRepository.getReferenceById(bookUuid.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testAuthorUuid() {
        AuthorsUuidEntity authorUuid = authorUuidRepository.save(new AuthorsUuidEntity());
        assertThat(authorUuid).isNotNull();
        assertThat(authorUuid.getId()).isNotNull();

        AuthorsUuidEntity fetched = authorUuidRepository.getReferenceById(authorUuid.getId());
        assertThat(fetched).isNotNull();
    }


    @Test
    void bookNaturalTest() {
        BooksNaturalEntity bookNatural = new BooksNaturalEntity();
        bookNatural.setTitle("My Book");
        BooksNaturalEntity saved = bookNaturalRepository.save(bookNatural);

        BooksNaturalEntity fetched = bookNaturalRepository.getReferenceById(saved.getTitle());
        assertThat(fetched).isNotNull();
    }

    @Test
    void authorCompositeTest() {
        NameId nameId = new NameId("John", "T");
        AuthorsCompositeEntity authorComposite = new AuthorsCompositeEntity();
        authorComposite.setFirstName(nameId.getFirstName());
        authorComposite.setLastName(nameId.getLastName());
        authorComposite.setCountry("US");

        AuthorsCompositeEntity saved = authorCompositeRepository.save(authorComposite);
        assertThat(saved).isNotNull();

        AuthorsCompositeEntity fetched = authorCompositeRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }

    @Test
    void authorEmbeddedTest() {
        NameId nameId = new NameId("John", "W");
        AuthorsEmbeddedEntity authorComposite = new AuthorsEmbeddedEntity();
        authorComposite.setNameId(nameId);
        authorComposite.setCountry("US");

        AuthorsEmbeddedEntity saved = authorEmbeddedRepository.save(authorComposite);
        assertThat(saved).isNotNull();

        AuthorsEmbeddedEntity fetched = authorEmbeddedRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }
}