package eu.dreamlabs.hibernatejpa.dao.method02jdbctemplate.mappers;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorMapper implements RowMapper<AuthorEntity> {
    @Override
    public AuthorEntity mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        // not thread safe !!!
        AuthorEntity author = new AuthorEntity();
        author.setId(rs.getLong("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));

        try {
            if (rs.getString("isbn") != null){
                author.setBooks(new ArrayList<>());
                author.getBooks().add(mapBook(rs));

                while (rs.next()){
                    author.getBooks().add(mapBook(rs));
                }
            }
        } catch (SQLException e) {
            //do nothing
        }
        return author;
    }

    private BookEntity mapBook(ResultSet rs) throws SQLException {
        BookEntity book = new BookEntity();
        book.setId(rs.getLong(4));
        book.setIsbn(rs.getString(5));
        book.setPublisher(rs.getString(6));
        book.setTitle(rs.getString(7));
        book.setAuthorId(rs.getLong(1));
        return book;
    }
}
