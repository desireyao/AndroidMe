package com.yaoh.view.glide;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaoh.view.R;

public class GlideActivity extends AppCompatActivity {

    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        imageview = (ImageView) findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(GlideActivity.this)
                        .load(Environment.getExternalStoragePublicDirectory("DCIM").getAbsolutePath() + "/Camera/IMG_20170204_013530.jpg")
                        .into(imageview);
            }
        });
    }
}
