package eu.dreamlabs.hibernatejpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter @Setter
public class BookEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(
    //        strategy = GenerationType.SEQUENCE,
    //        generator = "books_seq")
    //@SequenceGenerator(
    //        name = "books_seq",
    //       sequenceName = "books_seq",
    //        allocationSize = 1)
    private Long id;
    private String isbn;
    private String title;
    private String publisher;
    // we don't use jpa relationship here
    private Long authorId;


    public BookEntity(
            String title,
            String isbn,
            String publisher,
            Long authorId) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        BookEntity book = (BookEntity) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode(): 0;
    }
}