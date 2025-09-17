package eu.dreamlabs.hibernatejpa.repository;

import eu.dreamlabs.hibernatejpa.entity.composites.AuthorsEmbeddedEntity;
import eu.dreamlabs.hibernatejpa.entity.composites.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorsEmbeddedEntity, NameId> {
}
