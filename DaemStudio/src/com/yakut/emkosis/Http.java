package com.yakut.emkosis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yakut
 */
public class Http {

    public static List<String> get(String entity) {
        List<String> list = new ArrayList<>();

        try {
            String u = "http://localhost:8000/getTanks";
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;
            //bazen bu readline da takılıyor dikkat et
            while ((s = reader.readLine()) != null) {
                list.add(s);
            }
            reader.close();
            connection.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
