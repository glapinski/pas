package com.pas.repository;

import com.pas.model.IdTrait;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.pas.utils.ErrorInfo.ENTITY_ALREADY_EXIST_MESSAGE;
import static com.pas.utils.ErrorInfo.ENTITY_NOT_FOUND_MESSAGE;

@Slf4j
public class IRepositoryImpl<T extends IdTrait> implements IRepository<T> {

    private Map<UUID, T> objects = Collections.synchronizedMap(new HashMap<>());
    @Override
    public synchronized T add(T entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (objects.containsKey(entity.getId())) {
            log.info(ENTITY_ALREADY_EXIST_MESSAGE.toString());
        } else {
            objects.put(entity.getId(), entity);
        }
        return entity;
    }

    @Override
    public synchronized void delete(UUID id) {
        if (!objects.containsKey(id)) {
            log.info("Entity with provided id isnt present in app context");
        } else {
            T entity = findById(id).orElse(null);
            objects.remove(entity.getId());
        }
    }

    @Override
    public synchronized void delete(T entity) {
        if (!objects.containsValue(entity)) {
            objects.remove(entity);
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.toString());
        }
    }

    @Override
    @SneakyThrows
    public synchronized T update(UUID id, T entity) {
        if (!findById(id).isPresent()) {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.toString());
        } else {
            objects.replace(id, entity);
        }
        return entity;
    }

    @Override
    public boolean exists(String id) {
        return objects.containsKey(UUID.fromString(id));
    }

    @Override
    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(objects.get(id));
    }

    @Override
    public List<T> findAll() {
        return objects.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return objects.size();
    }

    public List<T> filter(Predicate<T> predicate) {
        return objects.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
