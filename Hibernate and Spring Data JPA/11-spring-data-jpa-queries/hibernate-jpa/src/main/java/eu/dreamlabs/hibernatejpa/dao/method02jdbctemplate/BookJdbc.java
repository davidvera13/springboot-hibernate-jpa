package eu.dreamlabs.hibernatejpa.dao.method02jdbctemplate;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;

public interface BookJdbc {
    BookEntity getById(Long id);

    BookEntity getByTitle(String title);

    BookEntity createBook(BookEntity book);

    BookEntity updateBook(BookEntity book);

    void deleteBookById(Long id);
}
