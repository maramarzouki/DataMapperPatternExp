package AfterPattern;

import java.sql.SQLException;
import java.util.List;

public interface DataMapper {
    User findById(int id) throws SQLException;

    List<User> findAll() throws SQLException;

    void save(User user) throws SQLException;

    void update(User user) throws SQLException;

    void delete(int id) throws SQLException;
}
