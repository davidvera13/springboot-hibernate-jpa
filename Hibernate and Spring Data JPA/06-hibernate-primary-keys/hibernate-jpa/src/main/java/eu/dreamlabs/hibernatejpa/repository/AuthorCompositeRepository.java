package eu.dreamlabs.hibernatejpa.repository;

import eu.dreamlabs.hibernatejpa.entity.composites.AuthorsCompositeEntity;
import eu.dreamlabs.hibernatejpa.entity.composites.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorsCompositeEntity, NameId> {
}
