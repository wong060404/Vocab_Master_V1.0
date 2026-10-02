package com.example.vocab_master_mad_project;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {

    private List<Vocab> studyList;
    private String mode;
    private String difficulty;
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        // Initialize and start background music looping
        mediaPlayer = MediaPlayer.create(this, R.raw.study_bgm);
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

        difficulty = getIntent().getStringExtra("DIFFICULTY");
        if (difficulty == null) difficulty = "Basic";
        
        mode = getIntent().getStringExtra("MODE"); // null for matching, "SPELLING" for spelling

        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(difficulty + ( "SPELLING".equals(mode) ? " Spelling List" : " Study List"));

        if ("SPELLING".equals(mode)) {
            studyList = VocabManager.getInstance().getRandomVocabs(difficulty, 10);
        } else {
            studyList = VocabManager.getInstance().getRandomVocabs(difficulty);
        }

        LinearLayout container = findViewById(R.id.ll_vocab_container);

        for (Vocab vocab : studyList) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_vocab_study, container, false);
            
            TextView tvEnglish = itemView.findViewById(R.id.tv_vocab_english);
            TextView tvPos = itemView.findViewById(R.id.tv_vocab_pos);
            TextView tvChinese = itemView.findViewById(R.id.tv_vocab_chinese);

            tvEnglish.setText(vocab.getEnglish());
            tvPos.setText(vocab.getPos());
            tvChinese.setText(vocab.getChinese());

            container.addView(itemView);
        }

        Button btnNext = findViewById(R.id.btn_next);
        if ("SPELLING".equals(mode)) {
            btnNext.setText("Start spelling");
        }

        btnNext.setOnClickListener(v -> {
            Intent intent;
            if ("SPELLING".equals(mode)) {
                intent = new Intent(StudyActivity.this, SpellingQuizActivity.class);
            } else {
                intent = new Intent(StudyActivity.this, QuizActivity.class);
            }
            ArrayList<Vocab> serializableList = new ArrayList<>(studyList);
            intent.putExtra("QUIZ_LIST", serializableList);
            startActivity(intent);
        });
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