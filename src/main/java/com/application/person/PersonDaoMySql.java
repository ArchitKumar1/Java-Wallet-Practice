package com.application.person;

import com.application.exceptions.PersonNotFoundException;

public class PersonDaoMySql implements IPersonDao{

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public Person getPerson(String name) throws PersonNotFoundException {
        return null;

    }

    @Override
    public void deletePerson(String name) {

    }

    @Override
    public Person getTopCustomer() {
        return null;
    }

    @Override
    public void removeFromSet(Person person) {

    }

    @Override
    public void addToSet(Person person) {

    }
}
