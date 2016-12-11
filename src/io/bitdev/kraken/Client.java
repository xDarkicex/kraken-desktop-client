package io.bitdev.kraken;

import com.oracle.javafx.jmx.json.JSONReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.Socket;

/**
 * Created by gentryrolofson on 12/10/16.
 */
public class Client {
    private Socket connection;
    private String lastTime = "0";
    public Client(String host, int port) throws IOException {
        connection = new Socket(host, port);
    }
    public void close() {
        System.out.println("Closing Kraken connection");
        try {
            connection.close();
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    private JsonReader exec(String[] args) {
        String output = "{\"Error\":true,\"Response\":{\"Message\":\"Error in asking Kraken\"}}";
        try {
            DataOutputStream outToServer = new DataOutputStream(connection.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            outToServer.writeBytes("status " + lastTime + '\n');
            output = inFromServer.readLine();
        } catch(IOException e) {
            System.err.println(e);
        } finally {
            return Json.createReader(new StringReader(output));
        }
    }
    public String status() {
        // Read JSON
        JsonReader reader = exec(new String[]{"status",lastTime});
        JsonObject object = reader.readObject();
        JsonObject response = object.getJsonObject("Response");
        if(!object.getBoolean("Error")) {
            // Unless there's an error...
            lastTime = response.getJsonArray("Data").getString(0);
            System.out.println("Updating Time to " + lastTime);
        }
        reader.close();
        return response.getString("Message");
    }
}
