package eu.dreamlabs.hibernatejpa.repository;

import eu.dreamlabs.hibernatejpa.entity.BooksUuidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookUuidRepository extends JpaRepository<BooksUuidEntity, UUID> {
}
