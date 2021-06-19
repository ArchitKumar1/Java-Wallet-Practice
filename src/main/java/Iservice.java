import java.util.List;

public interface Iservice {

    void addPersons(List<String> name);

    void removePersons(List<String> names);

    List<Person> getPersons(List<String> names) throws PersonNotFoundException;

    void transact(String sender, String reciever, int amount) throws NotEnoughBalanceException, PersonNotFoundException;

    Person getTopCustomer();
}
