package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            // Create a baseJsonObject from the json string
            JSONObject baseJsonObject = new JSONObject(json);

            // extract the JSONObject associated with the key called "name"
            JSONObject nameObject = baseJsonObject.getJSONObject("name");

            // Extract the value for the key called "mainName"
            String mainName = nameObject.getString("mainName");

            // Create an empty List that we can start adding alsoKnownAs
            List<String> alsoKnownAs = new ArrayList<String>();
            JSONArray alsoKnownAsJsonArray = nameObject.optJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.optString(i));
            }

            // Extract the value for the key called "placeOfOrigin"
            String placeOfOrigin = baseJsonObject.getString("placeOfOrigin");

            // Extract the value for the key called "description"
            String description = baseJsonObject.getString("description");

            // Extract the value for the key called "image"
            String image = baseJsonObject.getString("image");

            // Create an empty List that we can start adding ingredients
            List<String> ingredients = new ArrayList<String>();
            JSONArray ingredientsJsonArray = baseJsonObject.optJSONArray("ingredients");
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.optString(i));
            }

            // Create a new {@link Sandwich} object with the mainName, alsoKnownAs, placeOfOrigin,
            // description, image, and ingredients from json.
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
