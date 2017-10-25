package com.djac21.washingtonpostnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {
    String detailTitleString, detailImageString, detailDescriptionString, detailDateString, detailUrlString, detailAuthorString;
    TextView detailTitle, detailAuthor, detailDate, detailURL, detailDescription;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailTitleString = getIntent().getStringExtra("title");
        detailImageString = getIntent().getStringExtra("image");
        detailDescriptionString = getIntent().getStringExtra("description");
        detailDateString = getIntent().getStringExtra("date");
        detailAuthorString = getIntent().getStringExtra("author");
        detailUrlString = getIntent().getStringExtra("url");

        detailTitle = findViewById(R.id.detail_title);
        detailImage = findViewById(R.id.detail_image);
        detailDescription = findViewById(R.id.detail_description);
        detailDate = findViewById(R.id.detail_date);
        detailAuthor = findViewById(R.id.detail_author);
        detailURL = findViewById(R.id.detail_url);

        detailTitle.setText(detailTitleString);
        detailDescription.setText(detailDescriptionString);
        detailAuthor.setText(detailAuthorString);
        detailDate.setText(detailDateString);
        detailURL.setText(detailUrlString);

        Glide.with(this)
                .load(detailImageString)
                .placeholder(R.drawable.ic_loading)
                .into(detailImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
