package com.example.cyrilwelschen.spendingsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cyril on 04.05.19.
 *
 */

public class SecondaryInputActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_input_activity);
        TextView cat = findViewById(R.id.category);
        String catString = getIntent().getStringExtra("CATEGORY");

        // ImageView catImage = findViewById(R.id.secActivityCatImage);
        // int drawable = R.mipmap.ic_launcher_round;

        if (catString != null && !catString.isEmpty()) {
            cat.setText(catString);
            /*
            switch (catString) {
                case "Komisionen":
                    drawable = R.drawable.shopping;
                    break;
                case "Auswärts":
                    drawable = R.drawable.food;
                    break;
                case "NiceToHave":
                    drawable = R.drawable.nice_to_have;
                    break;
                case "Rechnung":
                    drawable = R.drawable.bank;
                    break;
            }
            */
        } else {
            cat.setText("No Category");
        }
        //catImage.setImageResource(drawable);
    }
}
