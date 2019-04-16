package com.aquent.crudapp.service;

import java.util.List;

public interface GenericService<T> {
    /**
     * @param domainObject
     * @return
     */
    public List<String> validate(T domainObject);

}
