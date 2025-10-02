package eu.dreamlabs.hibernatejpa.dao.method02jdbctemplate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;

public interface AuthorJdbc {
    AuthorEntity createAuthor(AuthorEntity entity);

    AuthorEntity getById(Long id);
    AuthorEntity getByIdWithBooks(Long id);
    AuthorEntity getByName(String firstName, String lastName);

    AuthorEntity updateAuthor(AuthorEntity entity);

    void deleteAuthor(Long id);
}
