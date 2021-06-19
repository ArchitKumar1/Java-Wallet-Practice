import java.util.ArrayList;
import java.util.List;


public class Service implements Iservice {

    private final PersonService personService;

    public Service(PersonService personService) {
        this.personService = personService;
    }

    public void addPersons(List<String> names) {
        personService.addPersons(names);
    }

    public void removePersons(List<String> names) {
        personService.removePersons(names);
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

        personService.removeFromSet(sender);
        personService.removeFromSet(reciever);

        int amount = transaction.getAmount();

        sender.getWallet().updateMoney(amount, PersonType.SENDER);
        reciever.getWallet().updateMoney(amount, PersonType.RECEIVER);

        personService.addToSet(sender);
        personService.addToSet(reciever);
        log("Sent " + amount + " from " + sender.getName() + " to " + reciever.getName() + " successfully");
    }

    private Person getPersonFromName(String name) throws PersonNotFoundException {
        return personService.getPerson(name);
    }

    public void transact(String sender, String reciever, int amount) throws NotEnoughBalanceException,
                                                                            PersonNotFoundException {
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

    private void validateTransaction(Transaction transaction) throws PersonNotFoundException,
                                                                     NotEnoughBalanceException {
        Person sender = getPersonFromName(transaction.getSendersName());
        if (sender.getWallet().getMoney() < transaction.amount) {
            throw new NotEnoughBalanceException(
                    String.format("Person %s Doesnt have enough Balance ", sender.getName()));
        }
    }

    public Person getTopCustomer() {
        return personService.getTopCustomer();
    }

    private void log(String message) {
        System.out.println(message);
    }

}
