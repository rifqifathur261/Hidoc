package tech.tedybear.hidoc.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tech.tedybear.hidoc.Preferences;
import tech.tedybear.hidoc.R;


public class WelcomeActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, tedy;
    Preferences preference;
    SharedPreferences sharedPreferences;
    Boolean firstTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preference = new Preferences(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        image = findViewById(R.id.logos);
        logo = findViewById(R.id.appname);
        tedy = findViewById(R.id.tedy);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        tedy.setAnimation(bottomAnim);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        firstTime = sharedPreferences.getBoolean("firstTime", true);

        if (firstTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    firstTime = false;
                    editor.putBoolean("firstTime", firstTime);
                    editor.apply();

                    Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 5000);
        } else {
            Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
