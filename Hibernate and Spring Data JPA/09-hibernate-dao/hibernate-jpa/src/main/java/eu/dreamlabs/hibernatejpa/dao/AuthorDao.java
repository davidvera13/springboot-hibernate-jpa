package eu.dreamlabs.hibernatejpa.dao;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;

public interface AuthorDao {
    AuthorEntity createAuthor(AuthorEntity entity);

    AuthorEntity getById(Long id);
    AuthorEntity getByIdPrepStatement(Long id);
    AuthorEntity getByName(String firstName, String lastName);

    AuthorEntity updateAuthor(AuthorEntity entity);

    void deleteAuthor(Long id);
}
