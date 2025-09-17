package eu.dreamlabs.hibernatejpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books_natural")
@NoArgsConstructor
@Getter @Setter
public class BooksNaturalEntity {
    @Id
    private String title;
    private String isbn;
    private String publisher;
}
