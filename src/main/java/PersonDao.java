import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class PersonDao {
    private final Map<String, Person> personMap;
    private final TreeSet<Person> personSet;

    public PersonDao() {
        this.personMap = new HashMap<>();
        this.personSet = new TreeSet<>();
    }

    public void addPerson(Person person) {
        personMap.put(person.getName(), person);
        personSet.add(person);
    }

    public Person getPerson(String name) throws PersonNotFoundException {
        if (!personMap.containsKey(name)) {
            throw new PersonNotFoundException(
                    String.format("Error : Person %s doesn't exist ", name));
        }
        return personMap.get(name);
    }

    public void deletePerson(String name) {
        personSet.remove(personMap.get(name));
        personMap.remove(name);
    }

    public Person getTopCustomer() {
        return personSet.first();
    }

    public boolean containsPerson(String name) {
        return personMap.containsKey(name);
    }

    public void removeFromSet(Person person) {
        personSet.remove(person);
    }

    public void addToSet(Person person) {
        personSet.add(person);
    }
}
