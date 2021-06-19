import java.util.List;

public class PersonService {
    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void addPersons(List<String> names) {
        for (int i = 0; i < names.size(); i++) {
            addPerson(names.get(i), i, Wallet.builder().
                    noOfTransactions(0).
                    money(1000).
                    build());
        }
    }

    public void addPerson(String name, int id, Wallet wallet) {
        Person person = Person.builder()
                .name(name)
                .wallet(wallet)
                .id(id)
                .build();
        personDao.addPerson(person);
    }

    public void removePersons(List<String> names) {
        for (String name : names) {
            removePerson(name);
        }
    }

    public void removePerson(String name) {
        personDao.deletePerson(name);
    }

    public Person getPerson(String name) throws PersonNotFoundException {
        return personDao.getPerson(name);
    }

    public Person getTopCustomer() {
        return personDao.getTopCustomer();
    }

    public boolean containsPerson(String name) {
        return personDao.containsPerson(name);
    }

    public void removeFromSet(Person person) {
        personDao.removeFromSet(person);
    }

    public void addToSet(Person person) {
        personDao.addToSet(person);
    }
}
