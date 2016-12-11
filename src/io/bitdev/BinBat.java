package io.bitdev;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;


/**
 * Created by gentryrolofson on 12/8/16.
 */
public class BinBat {
    private String lastTime = "0";
    public BinBat(){}
    public String exec() throws IOException {
        String output;
        Socket clientSocket = new Socket("localhost", 4980);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer.writeBytes("status " + lastTime + '\n');
        output = inFromServer.readLine();
        clientSocket.close();

        JsonReader reader = Json.createReader(new StringReader(output));
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



