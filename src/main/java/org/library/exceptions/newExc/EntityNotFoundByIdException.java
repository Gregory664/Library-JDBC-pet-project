package org.library.exceptions.newExc;

public class EntityNotFoundByIdException extends Exception {
    public EntityNotFoundByIdException(Class aClass, int id) {
        super(String.format("%s with id [%d] not found ", aClass.getName(), id));
    }
}
