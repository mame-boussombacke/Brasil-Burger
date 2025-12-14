package com.brasilburger.core.interfaces;
import com.brasilburger.exceptions.DataAccessException;
import java.util.List;

public interface IRepository<T extends IEntity> {
    void create(T entity) throws DataAccessException;
    void update(T entity) throws DataAccessException;
    void delete(int id) throws DataAccessException;
    T findById(int id) throws DataAccessException;
    List<T> findAll() throws DataAccessException;
}
