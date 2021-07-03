package com.application.person;

import com.application.exceptions.PersonNotFoundException;

public interface IPersonDao {
    void addPerson(Person person);

    Person getPerson(String name) throws PersonNotFoundException;

    void deletePerson(String name);

    Person getTopCustomer();

    void removeFromSet(Person person);

    void addToSet(Person person);
}
