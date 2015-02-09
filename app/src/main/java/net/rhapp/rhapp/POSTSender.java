package net.rhapp.rhapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * from http://www.itcuties.com/java/send-post-request/
 */
public class POSTSender extends AsyncTask<String, Integer, String> {


    public String sendMessage(String netidFrom, String netidTo, String message, boolean anon) throws IOException {

        // {'netid_from': ___, 'netid_to': ___, 'message': ___, 'anon': ___(boolean, 1 is yes) }

        // Connect
        URL url = new URL("http://abraunstein.pythonanywhere.com/api/message/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

//        // Set properties
//        connection.setRequestProperty("netid_from", netidFrom);
//        connection.setRequestProperty("netid_to", netidTo);
//        connection.setRequestProperty("message", message);
//        connection.setRequestProperty("anon", String.valueOf(anon));

        // create JSON
        JSONObject params = new JSONObject();
        try {
            params.put("netid_from", netidFrom);
            params.put("netid_to", netidTo);
            params.put("message", message);
            params.put("anon", anon);
        } catch (JSONException e) {
            Log.i("JSON error", e.getMessage());
            throw new IOException();
        }

        // Write
        OutputStream os = connection.getOutputStream();
        os.write(params.toString().getBytes());
        os.flush();

        // Read response
        StringBuilder responseSB = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ( (line = br.readLine()) != null)
            responseSB.append(line);

        // Close streams
        br.close();
//        os.close();

        return responseSB.toString();

    }

    @Override
    protected String doInBackground(String[] params) {

        String response = "";
        try {
            response = sendMessage(params[0], params[1], params[2], Boolean.parseBoolean(params[3]));
        } catch (IOException e) {
            e.printStackTrace();
            response = "";
        }

        return response;
    }
}
