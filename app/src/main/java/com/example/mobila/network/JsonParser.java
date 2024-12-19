package com.example.mobila.network;

import android.util.Log;

import com.example.mobila.data.DateConverter;
import com.example.mobila.data.Mobila;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonParser {
    public static List<Mobila> getFromJson(String json) {
        List<Mobila> lista = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject details = root.getJSONObject("details");
            JSONArray datasets = details.getJSONArray("datasets");

            for (int i = 0; i < datasets.length(); i++) {
                JSONObject object = datasets.getJSONObject(i);
                JSONObject mobila = object.getJSONObject("mobila");

                int weight = mobila.getInt("weight");
                String producer = mobila.getString("producer");
                String releaseDate = mobila.getString("releaseDate");

                Date date;
                try {
                    date = DateConverter.toDate(releaseDate);
                } catch (Exception ex) {
                    Log.e("parser", "Error parsing the date");
                    continue;
                }

                Mobila bucata = new Mobila(weight, producer, date);
                lista.add(bucata);
            }
            return lista;
        } catch(JSONException ex) {
            Log.e("parse", "Error parsing the json");
        }
        return new ArrayList<>();
    }
}
