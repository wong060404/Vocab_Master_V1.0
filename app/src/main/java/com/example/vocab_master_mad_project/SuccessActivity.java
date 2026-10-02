package com.example.vocab_master_mad_project;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        // Initialize and start clapping sound looping
        mediaPlayer = MediaPlayer.create(this, R.raw.clappinghands);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        Button btnHome = findViewById(R.id.btn_back_home);
        btnHome.setOnClickListener(v -> navigateToHome());
    }

    private void navigateToHome() {
        Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}