package com.example.vocab_master_mad_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Initialize VocabManager (and thus the database) early
        VocabManager.getInstance(this);

        // Get data from intent to decide where to go next
        final String difficulty = getIntent().getStringExtra("DIFFICULTY");
        final String mode = getIntent().getStringExtra("MODE");

        // Redirect after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (difficulty != null) {
                    // Coming from a ChooseActivity -> Go to StudyActivity
                    intent = new Intent(LoadingActivity.this, StudyActivity.class);
                    intent.putExtra("DIFFICULTY", difficulty);
                    intent.putExtra("MODE", mode);
                } else {
                    // Initial App Launch -> Go to MainActivity
                    intent = new Intent(LoadingActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish(); // Close LoadingActivity
            }
        }, 3000);
    }
}