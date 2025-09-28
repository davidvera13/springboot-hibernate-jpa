package eu.dreamlabs.hibernatejpa.hibernate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;

public interface AuthorHibernateDao {
    AuthorEntity createAuthor(AuthorEntity entity);

    AuthorEntity getById(Long id);
    AuthorEntity getByName(String firstName, String lastName);

    AuthorEntity updateAuthor(AuthorEntity entity);

    void deleteAuthor(Long id);
}
