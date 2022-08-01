package com.example.sellbuy.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{

    private final Long objectId;
    private final String typeOfObject;

    public ObjectNotFoundException(Long objectId, String typeOfObject) {
        this.objectId = objectId;
        this.typeOfObject = typeOfObject;
    }

    public Long getObjectId() {
        return objectId;
    }

    public String getTypeOfObject() {
        return typeOfObject;
    }
}
