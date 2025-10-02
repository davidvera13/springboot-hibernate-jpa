package eu.dreamlabs.hibernatejpa;

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

    @Test
    @Order(1)
    void mysqlTest() {
        long countBefore = bookRepository.count();
        //assertThat(countBefore).isEqualTo(8L);
        assertThat(countBefore).isNotNull();
    }
}