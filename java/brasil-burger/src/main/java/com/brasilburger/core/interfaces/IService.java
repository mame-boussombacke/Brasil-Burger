package com.brasilburger.core.interfaces;
import com.brasilburger.exceptions.BusinessException;
import java.util.List;

public interface IService<T extends IEntity> {
    void ajouter(T entity) throws BusinessException;
    void modifier(T entity) throws BusinessException;
    void supprimer(int id) throws BusinessException;
    T getById(int id) throws BusinessException;
    List<T> getAll() throws BusinessException;
}
