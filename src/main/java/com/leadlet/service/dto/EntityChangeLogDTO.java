package com.leadlet.service.dto;


import java.io.Serializable;

public class EntityChangeLogDTO implements Serializable {

    private String fieldName;

    private Object oldValue;

    private Object newValue;

    public EntityChangeLogDTO() {
    }

    public EntityChangeLogDTO(String fieldName, Object oldValue, Object newValue) {
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public EntityChangeLogDTO setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public EntityChangeLogDTO setOldValue(Object oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public Object getNewValue() {
        return newValue;
    }

    public EntityChangeLogDTO setNewValue(Object newValue) {
        this.newValue = newValue;
        return this;
    }
}
