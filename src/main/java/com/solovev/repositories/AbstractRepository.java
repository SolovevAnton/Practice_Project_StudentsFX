package com.solovev.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.model.Student;

import java.util.Collection;

public abstract class AbstractRepository<T> implements Repository<T> {
   private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public boolean add(T elem) {
        return false;
    }

    @Override
    public T delete(int elemId) {
        return null;
    }

    @Override
    public Collection<T> takeData() {
        return null;
    }

    @Override
    public T takeData(int elemId) {
        return null;
    }

    @Override
    public boolean replace(T newElem) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
