package eu.dreamlabs.hibernatejpa.bootstrap;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import eu.dreamlabs.hibernatejpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        BookEntity bookDDD = new BookEntity("Domain Driven Design", "Author1", "123", "RandomHouse");
        System.out.println("Id: " + bookDDD.getId() );
        BookEntity savedDDD = bookRepository.save(bookDDD);
        System.out.println("Id: " + savedDDD.getId() );

        BookEntity bookSIA = new BookEntity("Spring In Action", "Author2", "234234", "Oriely");
        BookEntity savedSIA = bookRepository.save(bookSIA);

        System.out.println("Id: " + savedSIA.getId() );

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book Id: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });

    }
}
