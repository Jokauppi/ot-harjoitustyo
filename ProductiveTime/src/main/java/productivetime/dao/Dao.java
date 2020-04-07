package productivetime.dao;

import productivetime.domain.Activity;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {
    void create(T object) throws SQLException;

    T read(K key) throws SQLException;
    T readLast() throws SQLException;
    T update(T object) throws SQLException;

    void delete(K key) throws SQLException;
    boolean deleteLast() throws SQLException;

    List<T> list() throws SQLException;
    List<T> list(long beginning, long end) throws SQLException;

    void clear() throws SQLException;
}
