package com.application;

import com.newrelic.api.agent.Trace;
import fi.iki.elonen.NanoHTTPD;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class WebServer extends NanoHTTPD {

    /**
     * Constructs an HTTP server on given port.
     *
     * @param port
     */
    ServiceRunner serviceRunner;

    public WebServer(int port) {
        super(port);
        this.serviceRunner = new Service();
        System.out.println("new service runner created");
    }

    /**
     * Constructs an HTTP server on given hostname and port.
     * d
     *
     * @param hostname
     * @param port
     */
    public WebServer(String hostname, int port) {
        super(hostname, port);
        this.serviceRunner = new Service();
        System.out.println("new service runner created");
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            URI uri = new URI(session.getUri());
            String path = uri.getPath();
            if (session.getMethod() == Method.POST) {
                return handlePostRequest(session, path);
            } else if (session.getMethod() == Method.GET) {
                return handleGetRequest(session, path);
            }
            return newFixedLengthResponse(Response.Status.BAD_REQUEST, MIME_PLAINTEXT,
                    "BAD_REQUEST");
        } catch (Exception exception) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    exception.getMessage());
        }
    }

    private Response handleGetRequest(IHTTPSession session, String path) {
        if (path.equals("/metrics")) {
            return exportMetrics();
        }
        return newFixedLengthResponse(Response.Status.METHOD_NOT_ALLOWED, MIME_PLAINTEXT,
                "Method Not Allowed");
    }

    private Response handlePostRequest(IHTTPSession session, String path) throws Exception {
        final HashMap<String, String> map = new HashMap<>();
        try {
            session.parseBody(map);
        } catch (IOException | ResponseException e) {
            e.printStackTrace();
        }
        String postData = map.get("postData");
        if (path.equals("/commands")) {
            return processCommands(postData);
        }
        if (path.equals("/set")) {
            return set(postData);
        }
        if (path.equals("/get")) {
            return get(postData);
        }
        return newFixedLengthResponse(Response.Status.METHOD_NOT_ALLOWED, MIME_PLAINTEXT,
                "Method Not Allowed");
    }

    @Trace(metricName = "RedisGet", dispatcher = true)
    private Response get(String postData) throws JSONException {
        JSONObject jsonObject = new JSONObject(postData);

        String key = jsonObject.getString("key");
        return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, serviceRunner.get(key));
    }

    @Trace(metricName = "RedisSet", dispatcher = true)
    private Response set(String postData) throws JSONException {

        JSONObject jsonObject = new JSONObject(postData);

        String key = jsonObject.getString("key");
        String value = jsonObject.getString("value");

        serviceRunner.set(key, value);
        return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "OK");
    }

    @Trace(metricName = "exportMetrics", dispatcher = true)
    private Response exportMetrics() {
        Writer writer = new StringWriter();
        try {
            TextFormat.write004(writer, CollectorRegistry.defaultRegistry.filteredMetricFamilySamples(new HashSet<>()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, writer.toString());
    }

    @Trace(metricName = "processCommands", dispatcher = true)
    private Response processCommands(String postData) throws Exception {
        JSONObject jsonObject = new JSONObject(postData);

        List<String> commands = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("commands");
        for (int i = 0; i < jsonArray.length(); i++) {
            String command = jsonArray.getString(i);
            commands.add(command);
        }
        String message;
        try {
            message = serviceRunner.processMessage(commands);
        } catch (Exception e) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    e.getMessage()); // Or postParameter.
        }
        return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, message);
    }
}
