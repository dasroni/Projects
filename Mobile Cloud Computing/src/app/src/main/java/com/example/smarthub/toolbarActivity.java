package com.example.smarthub;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class toolbarActivity extends AppCompatActivity
{
    TextView title;
    Animation animation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_tool);
        title = findViewById(R.id.titleId);

        animation = AnimationUtils.loadAnimation(this,R.anim.title_anim);
        title.setAnimation(animation);

    }


}
