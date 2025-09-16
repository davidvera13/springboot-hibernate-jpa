package eu.dreamlabs.hibernatejpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@Getter @Setter
public class AuthorEntity {
    // primary key
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(
    //        strategy = GenerationType.SEQUENCE,
    //        generator = "authors_seq")
    //@SequenceGenerator(
    //        name = "authors_seq",
    //        sequenceName = "authors_seq",
    //        allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorEntity author = (AuthorEntity) o;

        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}