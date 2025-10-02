package eu.dreamlabs.hibernatejpa.dao.method01datasource;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;

public interface BookDao {
    BookEntity getById(Long id);

    BookEntity getByTitle(String title);

    BookEntity createBook(BookEntity book);

    BookEntity updateBook(BookEntity book);

    void deleteBookById(Long id);
}
