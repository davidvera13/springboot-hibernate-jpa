package eu.dreamlabs.hibernatejpa.jdbctemplate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import eu.dreamlabs.hibernatejpa.jdbctemplate.mappers.AuthorMapper;
import eu.dreamlabs.hibernatejpa.jdbctemplate.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookJdbcImpl implements BookJdbc{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public BookEntity getById(Long id) {
        return jdbcTemplate
                .queryForObject(
                        "SELECT * FROM books where id = ?",
                        rowMapper(),
                        id);
    }



    @Override
    public BookEntity getByTitle(String title) {
        return jdbcTemplate
                .queryForObject(
                        "SELECT * FROM books where title = ?",
                        rowMapper(),
                        title);
    }

    @Override
    public BookEntity createBook(BookEntity book) {
        jdbcTemplate.update(
                "INSERT INTO book (isbn, publisher, title, author_id) " +
                        "VALUES (?, ?, ?, ?)",
                book.getIsbn(),
                book.getPublisher(),
                book.getTitle(),
                book.getAuthorId());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return getById(id);
    }

    @Override
    public BookEntity updateBook(BookEntity book) {
        jdbcTemplate.update(
                "UPDATE books SET isbn = ?, publisher = ?, title = ?, author_id = ? " +
                        "WHERE id = ?",
                book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId(), book.getId());

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE from books where id = ?", id);
    }

    private RowMapper<BookEntity> rowMapper() {
        return new BookMapper();
    }
}
