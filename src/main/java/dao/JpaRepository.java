package dao;


import paging.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface JpaRepository<T , ID> {
    <S extends T> S insert(T entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException;

    void update(T entity) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    void delete(String Id) throws NoSuchFieldException, SQLException;
    <S extends T> S findById(ID id);

    <S extends T> List<S> findAll(Pageable pageable) throws IllegalAccessException, NoSuchFieldException, InstantiationException, SQLException;

    long count() throws SQLException;
}
