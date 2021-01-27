package org.library.exceptions.newExc;

public class EntityNotFoundByTitleException extends Exception {
    public EntityNotFoundByTitleException(Class aClass, String title) {
        super(String.format("%s with id [%s] not found ", aClass.getName(), title));
    }
}
