package eu.dreamlabs.hibernatejpa.jdbctemplate.mappers;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import org.springframework.jdbc.core.RowMapper;

import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<BookEntity> {
    @Override
    public BookEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookEntity book = new BookEntity();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublisher(rs.getString("publisher"));
        book.setAuthorId(rs.getLong("author_id"));

        return book;
    }
}
