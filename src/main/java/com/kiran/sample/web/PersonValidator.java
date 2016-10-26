package com.kiran.sample.web;

import com.kiran.sample.domain.Person;
import com.kiran.sample.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by vashishta on 10/24/16.
 */
@Component
public class PersonValidator implements Validator {

    @Autowired
    private PersonService personService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(Person.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Person person = (Person) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required", "required");

        String name = person.getName();

        Person withName = personService.getByName(name);
        if (withName != null) {
            errors.reject("name", "Duplicate");
        }

    }
}
