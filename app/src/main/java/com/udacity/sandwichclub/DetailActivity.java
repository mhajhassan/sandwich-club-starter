package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Find the TextView with view ID origin_tv
        TextView originTextView = findViewById(R.id.origin_tv);

        // Find the TextView with view ID also_known_tv
        TextView alsoKnownTextView = findViewById(R.id.also_known_tv);

        // Find the TextView with view ID description_tv
        TextView descriptionTextView = findViewById(R.id.description_tv);

        // Find the TextView with view ID ingredients_tv
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        //check if origin is not empty, display origin of the current sandwich in that TextView, otherwise show no data available text
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTextView.setText(R.string.no_data_available);
        }

        //check if description is not empty, display description of the current sandwich in that TextView, otherwise show no data available text
        if (!sandwich.getDescription().isEmpty()) {
            descriptionTextView.setText(sandwich.getDescription());
        } else {
            descriptionTextView.setText(R.string.no_data_available);
        }

        //check if AlsoKnownAs is not empty, display AlsoKnownAs of the current sandwich in that TextView, otherwise show no data available text
        if (sandwich.getAlsoKnownAs().size() > 0) {
            // add all elements in one string and append them with (", ")
            StringBuilder knownAsText = new StringBuilder();
            for (String element : sandwich.getAlsoKnownAs()) {
                knownAsText.append(element).append(", ");
            }
            alsoKnownTextView.setText(knownAsText.toString());
        } else {
            alsoKnownTextView.setText(R.string.no_data_available);
        }

        //check if ingredients is not empty, display ingredients of the current sandwich in that TextView, otherwise show no data available text
        if (sandwich.getIngredients().size() > 0) {
            // add all elements in one string and append them with (", ")
            StringBuilder ingredientsText = new StringBuilder();
            for (String element : sandwich.getIngredients()) {
                ingredientsText.append(element).append(", ");
            }
            ingredientsTextView.setText(ingredientsText.toString());
        } else {
            ingredientsTextView.setText(R.string.no_data_available);
        }

    }
}
