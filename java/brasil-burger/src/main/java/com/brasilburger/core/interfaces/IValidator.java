package com.brasilburger.core.interfaces;
import com.brasilburger.exceptions.ValidationException;

public interface IValidator<T> {
    void validate(T entity) throws ValidationException;
}
