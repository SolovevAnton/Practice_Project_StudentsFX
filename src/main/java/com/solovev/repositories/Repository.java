package com.solovev.repositories;

import java.sql.SQLException;
import java.util.Collection;

public interface Repository<T> {
        boolean add (T elem);
        T delete(int elemId);
        Collection<T> takeData();
        T takeData(int elemId);
        boolean replace(T newElem);
        int size();
}
