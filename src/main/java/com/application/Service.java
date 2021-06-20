package com.application;

import com.application.exceptions.NotEnoughBalanceException;
import com.application.exceptions.PersonNotFoundException;
import com.application.person.Person;
import com.application.person.PersonDao;
import com.application.person.PersonService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Service implements Iservice {

    public static Service INSTANCE;
    private final PersonService personService;

    public Service(PersonService personService) {
        this.personService = personService;
    }

    public static synchronized Service getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Service(new PersonService(new PersonDao()));
        }
        return INSTANCE;
    }

    private void processTransaction(Transaction transaction) throws PersonNotFoundException {
        Person sender = personService.getPerson(transaction.getSendersName());
        Person reciever = personService.getPerson(transaction.getReceiversName());
        personService.removeFromSet(sender);
        personService.removeFromSet(reciever);

        int amount = transaction.getAmount();

        sender.getWallet().updateMoney(amount, PersonType.SENDER);
        reciever.getWallet().updateMoney(amount, PersonType.RECEIVER);

        personService.addToSet(sender);
        personService.addToSet(reciever);
        log("Sent " + amount + " from " + sender.getName() + " to " + reciever.getName() + " successfully");
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
        Person sender = personService.getPerson(transaction.getSendersName());
        if (sender.getWallet().getMoney() < transaction.amount) {
            throw new NotEnoughBalanceException(
                    String.format("Person %s Doesnt have enough Balance ", sender.getName()));
        }
    }

    private void log(String message) {
        System.out.println(message);
    }

    public void run(BufferedReader br) throws IOException {
        String str;
        label:
        while ((str = br.readLine()) != null) {
            System.out.println(str);
            String[] command = str.split(" ");
            List<String> commandList = Arrays.asList(command);
            switch (command[0]) {
                case "add": {
                    List<String> names = commandList.subList(1, commandList.size());
                    personService.addPersons(names);
                    break;
                }

                case "remove": {
                    List<String> names = commandList.subList(1, commandList.size());
                    personService.removePersons(names);
                    break;
                }
                case "transact":
                    try {
                        transact(command[1], command[2], Integer.parseInt(command[3]));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "status":
                    try {
                        List<String> names = commandList.subList(1, commandList.size());
                        List<Person> persons = personService.getPersons(names);
                        for (Person person : persons) {
                            System.out.println(person);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "top":
                    System.out.println(personService.getTopCustomer());
                    break;
                case "exit":
                    break label;
                default:
                    System.out.println("Try again");
                    break;
            }
        }
    }


}
