package eu.dreamlabs.hibernatejpa.repository;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
