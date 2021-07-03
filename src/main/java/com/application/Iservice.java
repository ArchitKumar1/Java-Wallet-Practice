package com.application;

import com.application.exceptions.NotEnoughBalanceException;
import com.application.exceptions.PersonNotFoundException;

import java.io.File;
import java.io.IOException;

public interface Iservice {

    void transact(String sender, String reciever, int amount) throws NotEnoughBalanceException, PersonNotFoundException;

    void run(File bufferedReader) throws IOException, Exception;

     void processCommand(String str) throws Exception;
}
