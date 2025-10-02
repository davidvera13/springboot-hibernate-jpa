package eu.dreamlabs.hibernatejpa.dao.method03hibernate;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;

import java.util.List;

public interface BookHibernateDao {
    List<BookEntity> findAll();
    BookEntity getById(Long id);
    BookEntity getByTitle(String title);
    BookEntity findByTitleNamedQuery(String title);
    BookEntity getByTitleCriteria(String title);
    BookEntity getByTitleNativeQuery(String title);

    BookEntity findByISBN(String isbn);

    BookEntity createBook(BookEntity book);

    BookEntity updateBook(BookEntity book);

    void deleteBookById(Long id);
}
