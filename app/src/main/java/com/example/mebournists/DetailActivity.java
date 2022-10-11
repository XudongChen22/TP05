package com.example.mebournists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;


// Class for showing more information when activity is clicked on
public class DetailActivity extends AppCompatActivity {
    Recommendation selectedRecommendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSelectedRecommendation();
        setValues();

    }

    private void getSelectedRecommendation()
    {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedRecommendation = getParsedRecommendation(parsedStringID);
    }

    private Recommendation getParsedRecommendation(String parsedID)
    {
        for (Recommendation recommendation: ActRecActivity.recList) {
            if (recommendation.getId().equals(parsedID))
                return recommendation;
        }
        return null;

    }

    // Setup values for detailed view
    private void setValues()
    {
        TextView tv = (TextView) findViewById(R.id.recName);
        tv.setText(selectedRecommendation.getName());

        TextView dv = (TextView) findViewById(R.id.descName);
        dv.setText(selectedRecommendation.getDescription());

        ImageView iv = (ImageView) findViewById(R.id.recImage);
        iv.setImageResource(selectedRecommendation.getImage());


    }
}