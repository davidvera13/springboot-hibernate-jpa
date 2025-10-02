package eu.dreamlabs.hibernatejpa.repository;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends
        JpaRepository<BookEntity, Long> {
}
