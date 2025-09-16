package eu.dreamlabs.hibernatejpa;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import eu.dreamlabs.hibernatejpa.repository.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// be careful or test orders:
// we can have issues with transactions / commits
// component scan will load DataInitializer to context and run it: entities will be created before testing
@DataJpaTest
@ActiveProfiles({"local"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"eu.dreamlabs.hibernatejpa.bootstrap"})
// we don't want spring to autoconfigure database and use h2, we want db to be configured based on profile with yaml file
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootJpaSliceWithBootstrappingDataInitializerTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    @Order(1)
    void testJpaTestSplice() {
        long countBefore = bookRepository.count();
        bookRepository.save(new BookEntity("My Book", "Author2", "1235555", "Self"));
        long countAfter = bookRepository.count();

        // now we use component scan to run bootstrap
        assertThat(countBefore).isEqualTo(2);
        assertThat(countBefore).isLessThan(countAfter);
        assertThat(countAfter).isEqualTo(countBefore + 1);
    }

    // default behaviour is to roll back the transaction ...
    @Test
    @Order(2)
    void testJpaTestSpliceTransaction() {
        long countBefore = bookRepository.count();
        // be aware of this ! we do not commit but already have 2 values in db
        assertThat(countBefore).isEqualTo(2);
    }

    // here transaction will be commited
    // we can either use @Rollback set to false or @Commit
    @Test
    @Order(3)
    // @Rollback(false)
    @Commit
    void testJpaTestSpliceNoRollback() {
        long countBefore = bookRepository.count();
        bookRepository.save(new BookEntity("My Book", "Author2", "1235555", "Self"));
        long countAfter = bookRepository.count();
        assertThat(countBefore).isLessThan(countAfter);
        assertThat(countAfter).isEqualTo(countBefore + 1);
    }

    // default behaviour is to roll back the transaction ...
    @Test
    @Order(4)
    void testJpaTestSpliceTransactionNoRollback() {
        long countBefore = bookRepository.count();
        // Error won't be triggered this time
        assertThat(countBefore).isEqualTo(3);
    }
}
