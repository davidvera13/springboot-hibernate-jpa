package eu.dreamlabs.hibernatejpa;

import eu.dreamlabs.hibernatejpa.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

// it loads springboot context
@SpringBootTest
class HibernateJpaApplicationTests {
    @Autowired
    BookRepository bookRepository;


    @Test
    void contextLoads() {
    }

    @Test
    void bookRepositoryCount() {
        long count = bookRepository.count();
        System.out.println("Number of books: " + count);
        // when srarting, books are initialized
        assertThat(count).isGreaterThan(0);
        // valid only if we have empty database at startup. Won't work in production mod
        // assertThat(count).isEqualTo(2);
    }
}
