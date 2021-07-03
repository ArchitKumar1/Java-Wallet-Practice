package com.application;

import java.util.List;


public interface ServiceRunner {

    default String processMessage(List<String> commands) {

        for (String command : commands) {
            try {
                processCommand(command);
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "OK";
    }

    void processCommand(String command) throws Exception;
}
