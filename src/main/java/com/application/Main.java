package com.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        // setup();
        run();
    }

    private static void setup() {
        Setup setupJob = new Setup();
        setupJob.run();
    }

    private static void run() throws IOException {
        Iservice service = Service.getInstance();
        File file = new File("src/main/java/com/application/input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        service.run(br);
    }
}
