package com.example.flickermsapps;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

public class FullsizePhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);

        String photo_url = getIntent().getStringExtra("url");
        photo_url = photo_url.substring(0,photo_url.length()-6);
        photo_url = photo_url.concat("_b.jpg");

        Glide.with(this).load(photo_url).override(Target.SIZE_ORIGINAL).into((ImageView) findViewById(R.id.iv_full_size_photo));


    }
}
