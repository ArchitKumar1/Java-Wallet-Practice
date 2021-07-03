package com.application;

import fi.iki.elonen.NanoHTTPD;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class Main {
    private static final int PORT = 8082;

    public static void main(String[] args) throws IOException {
        // run();
        runHttp();
    }

    private static void runHttp() throws IOException {
        WebServer webServer = new WebServer(PORT);
        webServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);

    }

    private static void run() throws Exception {
        Iservice service = new Service();
        File file = new File("src/main/java/com/application/input.txt");
        service.run(file);
    }
}
