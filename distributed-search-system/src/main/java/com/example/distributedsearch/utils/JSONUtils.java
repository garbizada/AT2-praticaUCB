package com.example.distributedsearch.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONUtils {
    public static List<String> searchInJSON(String filePath, String query) {
        List<String> results = new ArrayList<>();
        try {
            String content = Files.readString(Paths.get(filePath));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("title");
                String intro = obj.optString("introduction", "");

                if (title.contains(query) || intro.contains(query)) {
                    results.add("Título: " + title);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}