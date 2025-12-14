package com.brasilburger.core.abstracts;
import com.brasilburger.core.interfaces.IService;
import com.brasilburger.core.interfaces.IEntity;
import com.brasilburger.exceptions.BusinessException;

import java.util.List;

public abstract class BaseService<T extends IEntity> implements IService<T> {

    @Override
    public abstract void ajouter(T entity) throws BusinessException;

    @Override
    public abstract void modifier(T entity) throws BusinessException;

    @Override
    public abstract void supprimer(int id) throws BusinessException;

    @Override
    public abstract T getById(int id) throws BusinessException;

    @Override
    public abstract List<T> getAll() throws BusinessException;
}
