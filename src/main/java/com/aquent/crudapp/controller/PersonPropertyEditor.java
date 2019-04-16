package com.aquent.crudapp.controller;

import com.aquent.crudapp.domain.Person;

import java.beans.PropertyEditorSupport;

public class PersonPropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Person person = new Person();
        person.setPersonId(Integer.valueOf(text));
        setValue(person);
    }
}
