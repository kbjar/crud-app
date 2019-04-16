package com.aquent.crudapp.service.impl;

import com.aquent.crudapp.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Basic service that has a generic validator validation
 * @param <T> Person Or Client
 */
public abstract class DefaultGenericService<T> implements GenericService<T> {

    @Autowired
    private Validator validator;

    @Override
    public List<String> validate(T domainObject) {
        Set<ConstraintViolation<T>> violations = validator.validate(domainObject);
        List<String> errors = new ArrayList<String>(violations.size());
        for (ConstraintViolation<T> violation : violations) {
            errors.add(violation.getMessage());
        }
        Collections.sort(errors);
        return errors;
    }

}
