package eu.dreamlabs.hibernatejpa.entity.composites;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authors_embedded")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class AuthorsEmbeddedEntity {
    @EmbeddedId
    private NameId nameId;

    private String country;

}
