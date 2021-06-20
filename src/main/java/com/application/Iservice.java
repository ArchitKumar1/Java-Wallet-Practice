package com.application;

import com.application.exceptions.NotEnoughBalanceException;
import com.application.exceptions.PersonNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;

public interface Iservice {

    void transact(String sender, String reciever, int amount) throws NotEnoughBalanceException, PersonNotFoundException;

    void run(BufferedReader bufferedReader) throws IOException;
}
