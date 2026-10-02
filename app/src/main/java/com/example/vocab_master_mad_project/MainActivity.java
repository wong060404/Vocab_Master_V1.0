package com.example.vocab_master_mad_project;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and start background music looping
        mediaPlayer = MediaPlayer.create(this, R.raw.home_page_bgm);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        MaterialButton btnMute = findViewById(R.id.btn_mute);
        btnMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMuted = !isMuted;
                if (mediaPlayer != null) {
                    if (isMuted) {
                        mediaPlayer.setVolume(0, 0);
                        btnMute.setIconResource(android.R.drawable.ic_lock_silent_mode);
                    } else {
                        mediaPlayer.setVolume(1, 1);
                        btnMute.setIconResource(android.R.drawable.ic_lock_silent_mode_off);
                    }
                }
            }
        });

        // About me button logic
        MaterialButton btnAboutMe = findViewById(R.id.btn_aboutme);
        applyRainbowText(btnAboutMe);
        btnAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
            }
        });

        Button btnTapMatch = findViewById(R.id.btn_tap_match);
        btnTapMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TapMatchChooseActivity.class);
                startActivity(intent);
            }
        });

        Button btnSpelling = findViewById(R.id.btn_spelling);
        btnSpelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpellingChooseActivity.class);
                startActivity(intent);
            }
        });

        Button btnCustomize = findViewById(R.id.btn_customize);
        btnCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomizeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void applyRainbowText(MaterialButton button) {
        Shader textShader = new LinearGradient(0, 0, 150, 0,
                new int[]{
                        0xFFFF0000, // Red
                        0xFFFF7F00, // Orange
                        0xFFFFFF00, // Yellow
                        0xFF00FF00, // Green
                        0xFF0000FF, // Blue
                        0xFF4B0082, // Indigo
                        0xFF9400D3  // Violet
                }, null, Shader.TileMode.CLAMP);
        button.getPaint().setShader(textShader);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !isMuted) {
            mediaPlayer.start();
        } else if (mediaPlayer != null && !mediaPlayer.isPlaying() && isMuted) {
            // If it was playing but we resumed, keep it playing but silent
            mediaPlayer.start();
            mediaPlayer.setVolume(0, 0);
        }
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