package uk.ac.lincoln.students.bru14476346.moviefinder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by james on 30/11/2016.
 */
public class JsonMovie {

    final String TAG = "JsonParser.java";

    static String json = "";

    public String getJsonFromUrl(String url)
    {
        try
        {
            URL u = new URL(url);
            HttpURLConnection restConnection = (HttpURLConnection) u.openConnection();
            restConnection.setRequestMethod("GET");
            restConnection.setRequestProperty("Content-length", "0");
            restConnection.setUseCaches(false);
            restConnection.setAllowUserInteraction(false);
            restConnection.setConnectTimeout(10000);
            restConnection.setReadTimeout(10000);
            restConnection.connect();
            int status = restConnection.getResponseCode();

            switch(status)
            {
                case 200:
                case 201:

                    BufferedReader br = new BufferedReader(new InputStreamReader(restConnection.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();

                    // remember, you are storing the json as a stringy
                    try {
                        json = sb.toString();
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing data " + e.toString());
                    }
                    // return JSON String containing data to Tweet activity (or whatever your activity is called!)
                    return json;

            }
        }
     catch (MalformedURLException ex)
     {
         Log.e(TAG, "Malformed URL ");
     }
     catch (IOException ex)
     {
        Log.e(TAG, "IO Exception ");
     }
        return null;
    }
}

