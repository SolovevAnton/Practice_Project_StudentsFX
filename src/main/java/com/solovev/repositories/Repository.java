package com.solovev.repositories;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface Repository<T> {
        /**
         * Adds value to repository, returns added object or throws if object has not been added
         * @param elem
         * @return
         * @throws IOException
         */
        T add (T elem) throws IOException;
        T delete(int elemId) throws IOException;
        Collection<T> takeData() throws IOException;

        /**
         * Gets the element with given id
         * @param elemId id of the element
         * @return element with this id or throws if have not been found
         * @throws IOException
         */
        T takeData(int elemId) throws IOException;
        boolean replace(T newElem);

}
