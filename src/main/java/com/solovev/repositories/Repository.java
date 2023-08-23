package com.solovev.repositories;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface Repository<T> {
        boolean add (T elem);
        T delete(int elemId);
        Collection<T> takeData() throws IOException;

        /**
         * Gets the element with given id
         * @param elemId
         * @return
         * @throws IOException
         */
        T takeData(int elemId) throws IOException;
        boolean replace(T newElem);

}
