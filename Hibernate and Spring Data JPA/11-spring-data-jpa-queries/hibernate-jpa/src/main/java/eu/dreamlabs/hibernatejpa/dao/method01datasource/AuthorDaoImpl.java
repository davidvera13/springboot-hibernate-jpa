package eu.dreamlabs.hibernatejpa.dao.method01datasource;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;


@Component
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
    private final DataSource dataSource;

    @Override
    public AuthorEntity createAuthor(AuthorEntity entity) {
        ResultSet rs = null;
        PreparedStatement ps = null;

        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            ps = conn.prepareStatement("insert into authors (first_name, last_name) values (?, ?)");
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.execute();
            // works for mysql
            rs = stmt.executeQuery("SELECT LAST_INSERT_ID() FROM authors");

            if (rs.next()) {
                Long id = rs.getLong(1);
                return getById(id);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public AuthorEntity getById(Long id) {
        //Connection conn = null;
        //Statement stmt = null;
        ResultSet rs = null;

        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            //conn = dataSource.getConnection();
            //stmt = conn.createStatement();
            // using query string
            rs = stmt.executeQuery("select * from authors where id = " + id);
            if (rs.next()) {
                AuthorEntity author = new AuthorEntity();
                author.setId(rs.getLong("id"));
                author.setFirstName(rs.getString("first_name"));
                author.setLastName(rs.getString("last_name"));
                return author;
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            // try with resource was used ...
            //if (stmt != null) {
            //    stmt.close();
            //}
            //if(conn != null) {
            //    conn.close();
            //}
            //conn = dataSource.getConnection();
            //stmt = conn.createStatement();
        }
        return null;
    }

    @Override
    public AuthorEntity getByIdPrepStatement(Long id) {
        ResultSet rs = null;
        PreparedStatement ps = null;

        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            ps = conn.prepareStatement("select * from authors where id = ?");
            ps.setLong(1, id);
            //rs = stmt.executeQuery("select * from authors where id = " + id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return setAuthorEntity(rs);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public AuthorEntity getByName(String firstName, String lastName) {
        ResultSet rs = null;
        PreparedStatement ps = null;

        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            ps = conn.prepareStatement("select * from authors where first_name = ? and last_name = ?");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            rs = ps.executeQuery();
            if (rs.next()) {
                return setAuthorEntity(rs);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public AuthorEntity updateAuthor(AuthorEntity entity) {
        PreparedStatement ps = null;

        try (Connection conn = dataSource.getConnection()) {
            ps = conn.prepareStatement("update authors set first_name = ?, last_name = ? WHERE authors.id = ?");
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setLong(3, entity.getId());
            ps.execute();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return getById(entity.getId());
    }

    @Override
    public void deleteAuthor(Long id) {
        PreparedStatement ps;

        try (Connection conn = dataSource.getConnection()) {
            ps = conn.prepareStatement("delete from authors WHERE authors.id = ?");
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private AuthorEntity setAuthorEntity(ResultSet rs) throws SQLException {
        AuthorEntity author = new AuthorEntity();
        author.setId(rs.getLong("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));
        return author;
    }
}
