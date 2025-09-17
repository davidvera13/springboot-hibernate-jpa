package eu.dreamlabs.hibernatejpa.bootstrap;

import eu.dreamlabs.hibernatejpa.entity.AuthorsUuidEntity;
import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import eu.dreamlabs.hibernatejpa.entity.BooksUuidEntity;
import eu.dreamlabs.hibernatejpa.repository.AuthorUuidRepository;
import eu.dreamlabs.hibernatejpa.repository.BookRepository;
import eu.dreamlabs.hibernatejpa.repository.BookUuidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "default"})
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;


    @Override
    public void run(String... args) {
        // reset on start based, triggered on profile
        bookRepository.deleteAll();

        BookEntity bookDDD = new BookEntity("Domain Driven Design", "123", "RandomHouse", null);
        System.out.println("Id: " + bookDDD.getId() );

        BookEntity savedDDD = bookRepository.save(bookDDD);
        System.out.println("Id: " + savedDDD.getId() );

        BookEntity bookSIA = new BookEntity("Spring In Action", "234234", "Oriely", null);
        BookEntity savedSIA = bookRepository.save(bookSIA);

        System.out.println("Id: " + savedSIA.getId() );

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book Id: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });

        // create user with UUID key as string
        AuthorsUuidEntity authorUuid = new AuthorsUuidEntity();
        authorUuid.setFirstName("Frank");
        authorUuid.setLastName("Herbert");
        AuthorsUuidEntity savedAuthor = authorUuidRepository.save(authorUuid);
        System.out.println("Saved Author UUID: " + savedAuthor.getId() );

        // using UUID2
        BooksUuidEntity bookUuid = new BooksUuidEntity();
        bookUuid.setTitle("All About UUIDs");
        BooksUuidEntity savedBookUuid = bookUuidRepository.save(bookUuid);
        System.out.println("Saved Book UUID: " + savedBookUuid.getId());
    }
}
