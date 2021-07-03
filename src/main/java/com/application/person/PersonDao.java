package com.application.person;

import com.application.exceptions.PersonNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class PersonDao implements IPersonDao {
    private final Map<String, Person> personMap;
    private final TreeSet<Person> personSet;

    public PersonDao() {
        this.personMap = new HashMap<>();
        this.personSet = new TreeSet<>();
    }

    @Override
    public void addPerson(Person person) {
        personMap.put(person.getName(), person);
        personSet.add(person);
    }

    @Override
    public Person getPerson(String name) throws PersonNotFoundException {
        if (!personMap.containsKey(name)) {
            throw new PersonNotFoundException(
                    String.format("Error : Person %s doesn't exist ", name));
        }
        return personMap.get(name);
    }

    @Override
    public void deletePerson(String name) {
        personSet.remove(personMap.get(name));
        personMap.remove(name);
    }

    @Override
    public Person getTopCustomer() {
        return personSet.first();
    }

    @Override
    public void removeFromSet(Person person) {
        personSet.remove(person);
    }

    @Override
    public void addToSet(Person person) {
        personSet.add(person);
    }
}
