package eu.dreamlabs.hibernatejpa.hibernate;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;

public interface BookHibernateDao {
    BookEntity getById(Long id);

    BookEntity getByTitle(String title);

    BookEntity createBook(BookEntity book);

    BookEntity updateBook(BookEntity book);

    void deleteBookById(Long id);
}
