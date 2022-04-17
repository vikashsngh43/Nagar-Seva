package com.example.googleapiimplimentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Full_image extends AppCompatActivity {
    ImageView myImage;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        url = getIntent().getStringExtra("image_url");

        myImage = findViewById(R.id.myImage);
        Glide.with(this).load(url)
                .placeholder(R.drawable.gops)
                .error(R.drawable.card_bg_gradient)
                .into(myImage);
    }
}