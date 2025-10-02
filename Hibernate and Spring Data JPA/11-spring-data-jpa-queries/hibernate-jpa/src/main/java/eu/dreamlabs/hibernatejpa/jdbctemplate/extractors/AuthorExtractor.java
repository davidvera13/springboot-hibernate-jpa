package eu.dreamlabs.hibernatejpa.jdbctemplate.extractors;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import eu.dreamlabs.hibernatejpa.jdbctemplate.mappers.AuthorMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorExtractor implements ResultSetExtractor<AuthorEntity> {
    @Override
    public AuthorEntity extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        rs.next();

        return new AuthorMapper().mapRow(rs, 0);
    }
}