package productivetime.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    void create(T object, long time) throws SQLException;

    T read(Integer key) throws SQLException;
    T readLast() throws SQLException;
    T update(T object, long time) throws SQLException;
    T retype(T object, String newType) throws SQLException;

    void delete(Integer key) throws SQLException;
    void deleteLast() throws SQLException;

    List<T> list() throws SQLException;
    List<T> list(long beginning, long end) throws SQLException;
    List<String> listTypes() throws SQLException;

    void clear() throws SQLException;
}
