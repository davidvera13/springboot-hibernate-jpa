package eu.dreamlabs.hibernatejpa.dao.method03hibernate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;

import java.util.List;

public interface AuthorHibernateDao {
    AuthorEntity createAuthor(AuthorEntity entity);

    List<AuthorEntity> findAll();
    AuthorEntity getById(Long id);
    AuthorEntity getByName(String firstName, String lastName);
    AuthorEntity getByNameNamedQuery(String firstName, String lastName);
    AuthorEntity getByNameCriteria(String firstName, String lastName);
    AuthorEntity getByNameNativeQuery(String firstName, String lastName);

    List<AuthorEntity> listAuthorByLastNameLike(String lastName);


    AuthorEntity updateAuthor(AuthorEntity entity);

    void deleteAuthor(Long id);
}
