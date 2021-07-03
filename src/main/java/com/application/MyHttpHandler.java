package com.application;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MyHttpHandler implements HttpHandler {

    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param httpExchange the exchange containing the request from the
     *                     client and used to send the response
     */
    @Override
    public void handle(HttpExchange httpExchange) {
        String requestParamValue = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(httpExchange);
        }
        try {
            handleResponse(httpExchange, requestParamValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException, JSONException {
        OutputStream outputStream = httpExchange.getResponseBody();

        // encode HTML content
        String htmlBuilder = "<html>" +
                "<body>" +
                "<h1>" +
                "Hello " +
                requestParamValue +
                "</h1>" +
                "</body>" +
                "</html>";
        System.out.println(htmlBuilder);
        JSONObject item = new JSONObject();

        item.put("name", "archit kumar");
        item.put("age", 123);
        item.put("city", "mumbai");

        System.out.print(item.toString());
        httpExchange.sendResponseHeaders(200, htmlBuilder.length());
        outputStream.write(item.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}

