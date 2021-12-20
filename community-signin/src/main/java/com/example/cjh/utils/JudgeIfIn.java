
package com.example.cjh.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class JudgeIfIn {
    public JudgeIfIn() {
    }

    public static boolean ifIn(String xy) throws Exception {
        String url = "https://tsapi.amap.com/v1/track/geofence/status/location?key=2feceabe59e494ef9e07fe57e0bfaf1d&sid=482358&gfids=404560&location=";
        url = url + xy;
        System.out.println(openGetRequest(url));
        String result = openGetRequest(url);
        char x = result.charAt(result.indexOf("\"in\"") + 5);
        return x == '1';
    }

    public static String openPostRequest(String request_url, String json_string) throws Exception {
        String response = postLoadJSON(request_url, json_string);
        return response;
    }

    public static String openGetRequest(String url) throws Exception {
        String response = getLoadJSON(url);
        return response;
    }

    private static String postLoadJSON(String url, String param) throws Exception {
        StringBuffer buffer = new StringBuffer();
        PrintWriter out = null;
        URL openUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)openUrl.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.connect();
        out = new PrintWriter(connection.getOutputStream());
        out.println(param);
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputLine = null;

        while((inputLine = reader.readLine()) != null) {
            buffer.append(inputLine);
        }

        reader.close();
        return buffer.toString();
    }

    private static String getLoadJSON(String url) throws Exception {
        StringBuffer buffer = new StringBuffer();
        URL openUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)openUrl.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputLine = null;

        while((inputLine = reader.readLine()) != null) {
            buffer.append(inputLine);
        }

        reader.close();
        return buffer.toString();
    }
}
