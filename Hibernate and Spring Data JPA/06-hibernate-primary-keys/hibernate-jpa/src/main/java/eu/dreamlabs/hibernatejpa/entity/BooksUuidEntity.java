package eu.dreamlabs.hibernatejpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "books_uuid")
@NoArgsConstructor
@Getter @Setter
public class BooksUuidEntity {
    // more efficient to store as UUID binary value
    @Id
    @GeneratedValue(generator = "uuid2")
    @UuidGenerator // default is uuid2
    @Column(
            columnDefinition = "BINARY(16)",
            updatable = false,
            nullable = false)
    private UUID id;

    private String title;
    private String isbn;
    private String publisher;
}
