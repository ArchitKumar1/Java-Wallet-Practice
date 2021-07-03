package com.application.person;

import com.application.Wallet;
import com.application.exceptions.PersonNotFoundException;

import java.util.List;

public interface IPersonService {

    void addPersons(List<String> names);

    void addPerson(String name, int id, Wallet wallet);

    void removePersons(List<String> names);

    void removePerson(String name);

    Person getPerson(String name) throws PersonNotFoundException;

    List<Person> getPersons(List<String> names) throws PersonNotFoundException;

    Person getTopCustomer();

    void removeFromSet(Person person);

    void addToSet(Person person);
}
