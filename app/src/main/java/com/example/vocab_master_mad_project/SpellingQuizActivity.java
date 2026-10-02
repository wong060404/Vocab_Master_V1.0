package com.example.vocab_master_mad_project;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class SpellingQuizActivity extends AppCompatActivity {

    private List<Vocab> quizList;
    private int currentIndex = 0;

    private TextView tvWordCount;
    private TextView tvChineseHint;
    private TextView tvPosHint;
    private EditText etSpellingInput;
    private Button btnSubmit;
    private Button btnBackToStudy;
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_quiz);

        // Initialize and start background music looping
        mediaPlayer = MediaPlayer.create(this, R.raw.quizing_bgm);
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

        quizList = (List<Vocab>) getIntent().getSerializableExtra("QUIZ_LIST");
        if (quizList == null) quizList = new ArrayList<>();

        tvWordCount = findViewById(R.id.tv_word_count);
        tvChineseHint = findViewById(R.id.tv_chinese_hint);
        tvPosHint = findViewById(R.id.tv_pos_hint);
        etSpellingInput = findViewById(R.id.et_spelling_input);
        btnSubmit = findViewById(R.id.btn_submit_spelling);
        btnBackToStudy = findViewById(R.id.btn_back_to_study);

        showNextWord();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSpelling();
            }
        });

        btnBackToStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Just finishes the quiz activity to go back to the previous study activity
            }
        });
    }

    private void showNextWord() {
        if (currentIndex < quizList.size()) {
            Vocab currentVocab = quizList.get(currentIndex);
            tvWordCount.setText("Word " + (currentIndex + 1) + " / " + quizList.size());
            tvChineseHint.setText(currentVocab.getChinese());
            tvPosHint.setText(currentVocab.getPos());
            etSpellingInput.setText("");
        } else {
            // Finished all words
            Intent intent = new Intent(SpellingQuizActivity.this, SuccessActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void checkSpelling() {
        String userInput = etSpellingInput.getText().toString().trim();
        Vocab currentVocab = quizList.get(currentIndex);

        if (userInput.equalsIgnoreCase(currentVocab.getEnglish())) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            currentIndex++;
            showNextWord();
        } else {
            Toast.makeText(this, "Wrong! Try again.", Toast.LENGTH_SHORT).show();
            etSpellingInput.selectAll();
        }
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