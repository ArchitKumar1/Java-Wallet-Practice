import java.util.*;

public class Service implements Iservice {
    Map<String, Person> personMap;
    TreeSet<Person> personSet;
    RedisClient redisClient = new RedisClient();

    Service() {
        personMap = new HashMap<>();
        personSet = new TreeSet<>();
    }

    public void addPassword(String name, String password) {
        redisClient.set(name, password);
    }

    public String getPassword(String name) {
        return redisClient.get(name);
    }

    public void addPersons(List<String> names) {
        for (String name : names) {
            addPerson(name);
        }
    }

    private void addPerson(String name) {
        Wallet wallet = getWallet();
        Person person = getPerson(name, wallet);
        personMap.put(person.getName(), person);
        log("Person " + name + " added");
    }

    private Person getPerson(String name, Wallet wallet) {
        return Person.builder().name(name).wallet(wallet).build();
    }

    private Wallet getWallet() {
        return Wallet.builder().money(1000).noOfTransactions(0).build();
    }

    public void removePersons(List<String> names) {
        for (String name : names) {
            removePerson(name);
        }
    }

    private void removePerson(String name) {
        Person person = personMap.get(name);
        personSet.remove(person);
        personMap.remove(name);
        log("Person " + name + " removed");
    }

    public List<Person> getPersons(List<String> names) throws PersonNotFoundException {
        List<Person> personList = new ArrayList<>();
        for (String name : names) {
            personList.add(getPerson(name));
        }
        return personList;
    }

    private Person getPerson(String name) throws PersonNotFoundException {

        return getPersonFromName(name);
    }

    private void processTransaction(Transaction transaction) throws PersonNotFoundException {
        Person sender = getPersonFromName(transaction.getSendersName());
        Person reciever = getPersonFromName(transaction.getReceiversName());

        removeFromSet(sender);
        removeFromSet(reciever);

        int amount = transaction.getAmount();

        sender.getWallet().updateMoney(amount, PersonType.SENDER);
        reciever.getWallet().updateMoney(amount, PersonType.RECEIVER);

        addToSet(sender);
        addToSet(reciever);
        log("Sent " + amount + " from " + sender.getName() + " to " + reciever.getName() + " successfully");
    }

    private void removeFromSet(Person person) {
        personSet.remove(person);
    }

    private void addToSet(Person person) {
        personSet.add(person);
    }


    private Person getPersonFromName(String name) throws PersonNotFoundException {
        if (!personMap.containsKey(name))
            throw new PersonNotFoundException(String.format("Person %s doesn't exist ", name));
        return personMap.get(name);
    }

    public void transact(String sender, String reciever, int amount) throws NotEnoughBalanceException, PersonNotFoundException {

        Transaction transaction = createTransaction(sender, reciever, amount);
        validateTransaction(transaction);
        processTransaction(transaction);
    }

    private Transaction createTransaction(String sender, String reciever, int amount) {
        return Transaction.builder()
                .amount(amount)
                .receiversName(reciever)
                .sendersName(sender)
                .build();
    }

    private void validateTransaction(Transaction transaction) throws PersonNotFoundException, NotEnoughBalanceException {

        Person sender = getPersonFromName(transaction.getSendersName());
        if (sender.getWallet().getMoney() < transaction.amount) {
            throw new NotEnoughBalanceException(String.format("Person %s Doesnt have enough Balance ", sender.getName()));
        }
    }

    public Person getTopCustomer() {
        return personSet.first();
    }

    private void log(String message) {
        System.out.println(message);
    }

}
