package eu.dreamlabs.hibernatejpa.jdbctemplate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import eu.dreamlabs.hibernatejpa.jdbctemplate.extractors.AuthorExtractor;
import eu.dreamlabs.hibernatejpa.jdbctemplate.mappers.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorJdbcImpl implements AuthorJdbc{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public AuthorEntity createAuthor(AuthorEntity entity) {
        jdbcTemplate.update(
                "INSERT INTO authors (first_name, last_name) VALUES (?, ?)",
                entity.getFirstName(),
                entity.getLastName());
        Long id = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Long.class);
        return getById(id);
    }

    @Override
    public AuthorEntity getById(Long id) {
        return jdbcTemplate
                .queryForObject(
                        "SELECT * FROM authors WHERE id = ?",
                        rowMapper(),
                        id);
    }

    public AuthorEntity getByIdWithBooks(Long id) {
        // query for object returns single value
        String sql = "SELECT authors.id as id, first_name, last_name, books.id as book_id, books.isbn, books.publisher, books.title " +
                "FROM authors " +
                "LEFT OUTER JOIN books on authors.id = books.author_id " +
                "WHERE authors.id = ?";
        return jdbcTemplate
                .query(
                        sql,
                        new AuthorExtractor(),
                        id);
    }

    @Override
    public AuthorEntity getByName(String firstName, String lastName) {
        return jdbcTemplate
                .queryForObject(
                        "SELECT * FROM authors WHERE " +
                                "first_name = ? AND " +
                                "last_name = ?",
                        rowMapper(),
                        firstName, lastName);
    }

    @Override
    public AuthorEntity updateAuthor(AuthorEntity entity) {
        jdbcTemplate.update("UPDATE authors SET first_name = ?, last_name = ? WHERE id = ?",
                entity.getFirstName(), entity.getLastName(), entity.getId());
        return getById(entity.getId());
    }

    @Override
    public void deleteAuthor(Long id) {
        jdbcTemplate.update("DELETE FROM authors WHERE id = ?", id);
    }

    private RowMapper<AuthorEntity> rowMapper() {
        return new AuthorMapper();
    }
}
