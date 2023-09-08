package com.solovev.repositories;

import java.io.IOException;
import java.util.Collection;

public interface Repository<T> {
        /**
         * Adds value to repository, returns added object or throws IllegalArgument if object has not been added
         * @param elem element to add
         * @return added element
         * @throws IOException if IO exception occurs
         */
        T add (T elem) throws IOException;
        T delete(int elemId) throws IOException;
        Collection<T> takeData() throws IOException;

        /**
         * Gets the element with given id throws IllegalArgument if this id is not presented in the DB
         * @param elemId id of the element
         * @return element with this id or throws if not found
         * @throws IOException if IO exception occurs
         */
        T takeData(int elemId) throws IOException;

        /**
         * replaces object in the DB;
         * replaces object by ID; throws IllegalArgument if ID cannot be replaced
         * @param newElem element to replace old one with
         * @return replaced object or throws if not found
         */
        T replace(T newElem) throws IOException;

}
