package com.brasilburger.core.abstracts;
import com.brasilburger.core.interfaces.IRepository;
import com.brasilburger.core.interfaces.IEntity;
import com.brasilburger.exceptions.DataAccessException;
import java.util.List;

public abstract class BaseRepository<T extends IEntity> implements IRepository<T> {

    @Override
    public abstract void create(T entity) throws DataAccessException;

    @Override
    public abstract void update(T entity) throws DataAccessException;

    @Override
    public abstract void delete(int id) throws DataAccessException;

    @Override
    public abstract T findById(int id) throws DataAccessException;

    @Override
    public abstract List<T> findAll() throws DataAccessException;
}
