package com.application;

import com.application.exceptions.NotEnoughBalanceException;
import com.application.exceptions.PersonNotFoundException;
import com.application.person.Person;
import com.application.person.PersonDao;
import com.application.person.PersonService;
import com.application.redis.RedisClient;
import com.newrelic.api.agent.Trace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;


public class Service implements Iservice, ServiceRunner {

    private final PersonService personService;

    public Service() {
        this.personService = new PersonService(new PersonDao());
    }

    private void processTransaction(Transaction transaction) throws PersonNotFoundException {
        Person sender = personService.getPerson(transaction.getSendersName());
        Person receiver = personService.getPerson(transaction.getReceiversName());
        personService.removeFromSet(sender,receiver);

        int amount = transaction.getAmount();

        sender.getWallet().updateMoney(amount, PersonType.SENDER, sender);
        receiver.getWallet().updateMoney(amount, PersonType.RECEIVER, receiver);

        personService.addToSet(sender);
        personService.addToSet(receiver);
        log("Sent " + amount + " from " + sender.getName() + " to " + receiver.getName() + " successfully");
    }


    public void transact(String sender, String receiver, int amount) throws NotEnoughBalanceException,
                                                                            PersonNotFoundException {
        Transaction transaction = createTransaction(sender, receiver, amount);
        validateTransaction(transaction);
        processTransaction(transaction);
    }

    private Transaction createTransaction(String sender, String receiver, int amount) {
        return Transaction.builder()
                .amount(amount)
                .receiversName(receiver)
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

    public void run(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        while ((str = br.readLine()) != null) {
            System.out.println(str);
            processCommand(str);
        }
    }

    @Trace(metricName = "processCommand",dispatcher = true)
    public void processCommand(String command) throws Exception {
        String[] commandWords = command.split(" ");
        List<String> commandList = Arrays.asList(commandWords);
        switch (commandWords[0]) {
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
                transact(commandWords[1], commandWords[2], Integer.parseInt(commandWords[3]));
                break;
            case "status":
                List<String> names = commandList.subList(1, commandList.size());
                List<Person> persons = personService.getPersons(names);
                for (Person person : persons) {
                    System.out.println(person);
                }
                break;
            case "top":
                System.out.println(personService.getTopCustomer());
                break;
            case "exit":
                break;
            default:
                throw new Exception(" Invalid Command  " + command);
        }
    }

    public void set(String key, String value) {
        RedisClient redisClient = new RedisClient();
        redisClient.set(key, value);
    }

    public String get(String key) {
        RedisClient redisClient = new RedisClient();
        return redisClient.get(key);
    }
}
