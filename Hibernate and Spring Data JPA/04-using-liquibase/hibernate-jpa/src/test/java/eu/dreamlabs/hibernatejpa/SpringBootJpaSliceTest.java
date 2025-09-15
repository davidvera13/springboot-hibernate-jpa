package eu.dreamlabs.hibernatejpa;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import eu.dreamlabs.hibernatejpa.repository.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

// load jpa context not all spring boot complete dependencies
@DataJpaTest
@ActiveProfiles({"local"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// we don't want spring to autoconfigure database and use h2, we want db to be configured based on profile with yaml file
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootJpaSliceTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    @Order(1)
    void testJpaTestSplice() {
        long countBefore = bookRepository.count();
        System.out.println("Number of books before: " + countBefore);
        bookRepository.save(new BookEntity("My Book", "1235555", "Self", null));
        long countAfter = bookRepository.count();
        System.out.println("Number of books after: " + countAfter);

        assertThat(countBefore).isLessThan(countAfter);
        assertThat(countAfter).isEqualTo(countBefore + 1);
        // we don't trigger command line runner call
        assertThat(countBefore).isEqualTo(0);
    }

    // default behaviour is to roll back the transaction: this test will fail if run alone
    @Test
    @Order(2)
    void testJpaTestSpliceTransaction() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(0);
    }

    // here transaction will be commited
    // we can either use @Rollback set to false or @Commit
    @Test
    @Order(3)
    // @Rollback(false)
    @Commit
    void testJpaTestSpliceNoRollback() {
        long countBefore = bookRepository.count();
        bookRepository.save(new BookEntity("My Book", "1235555", "Self", null));
        long countAfter = bookRepository.count();

        // Expected :2L
        // Actual   :0L
        // assertThat(countBefore).isEqualTo(2);
        assertThat(countBefore).isLessThan(countAfter);
        assertThat(countAfter).isEqualTo(countBefore + 1);
    }

    // default behaviour is to roll back the transaction ...
    // this test must be run after testJpaTestSpliceNoRollback
    @Test
    @Order(4)
    void testJpaTestSpliceTransactionNoRollback() {
        long countBefore = bookRepository.count();
        // Error won't be triggered this time
        assertThat(countBefore).isEqualTo(1);
    }
}
