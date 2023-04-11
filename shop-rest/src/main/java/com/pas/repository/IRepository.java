package com.pas.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public interface IRepository<T> {
    T add(T entity);

    void delete(UUID id);

    void delete(T entity);

    T update(UUID id, T entity);

    boolean exists(String id);

    Optional<T> findById(UUID id);

    List<T> findAll();

    int size();

    List<T> filter(Predicate<T> predicate);
}
