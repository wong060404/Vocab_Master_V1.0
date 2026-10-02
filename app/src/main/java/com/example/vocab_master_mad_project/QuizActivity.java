package com.example.vocab_master_mad_project;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private List<Vocab> quizList;
    private Map<String, String> userMatches = new HashMap<>(); // English -> Chinese
    private Map<String, Integer> pairColors = new HashMap<>(); // English -> Color Int
    private String selectedEnglish = null;
    private TextView selectedEnglishView = null;

    private LinearLayout englishColumn;
    private LinearLayout chineseColumn;
    private List<TextView> englishViews = new ArrayList<>();
    private List<TextView> chineseViews = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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

        // Retrieve the quiz list from the Intent
        List<Vocab> list = (List<Vocab>) getIntent().getSerializableExtra("QUIZ_LIST");
        if (list != null) {
            quizList = new ArrayList<>(list);
        } else {
            quizList = new ArrayList<>();
        }

        englishColumn = findViewById(R.id.ll_english_column);
        chineseColumn = findViewById(R.id.ll_chinese_column);

        setupBoard();

        Button btnFinish = findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(v -> validateResults());
    }

    private void setupBoard() {
        englishColumn.removeAllViews();
        chineseColumn.removeAllViews();
        englishViews.clear();
        chineseViews.clear();
        userMatches.clear();
        pairColors.clear();
        selectedEnglish = null;
        selectedEnglishView = null;

        List<String> englishWords = new ArrayList<>();
        List<String> chineseMeanings = new ArrayList<>();
        for (Vocab v : quizList) {
            englishWords.add(v.getEnglish());
            chineseMeanings.add(v.getChinese());
        }

        Collections.shuffle(englishWords);
        Collections.shuffle(chineseMeanings);

        LayoutInflater inflater = LayoutInflater.from(this);

        for (String eng : englishWords) {
            TextView tv = (TextView) inflater.inflate(R.layout.item_quiz_box, englishColumn, false);
            tv.setText(eng);
            tv.setOnClickListener(v -> handleEnglishClick(tv, eng));
            englishViews.add(tv);
            englishColumn.addView(tv);
        }

        for (String chi : chineseMeanings) {
            TextView tv = (TextView) inflater.inflate(R.layout.item_quiz_box, chineseColumn, false);
            tv.setText(chi);
            tv.setOnClickListener(v -> handleChineseClick(tv, chi));
            chineseViews.add(tv);
            chineseColumn.addView(tv);
        }
    }

    private void handleEnglishClick(TextView tv, String word) {
        // If this word was already matched, allow re-selection but we'll clear its previous pair visual in handleChinese
        if (selectedEnglishView != null && selectedEnglishView != tv) {
             // If we had a selection that wasn't paired yet, reset its color
             if (!userMatches.containsKey(selectedEnglish)) {
                 selectedEnglishView.setBackgroundResource(android.R.drawable.btn_default_small);
                 selectedEnglishView.setTextColor(Color.BLACK);
             }
        }

        selectedEnglish = word;
        selectedEnglishView = tv;
        tv.setBackgroundColor(Color.BLUE);
        tv.setTextColor(Color.WHITE);
    }

    private void handleChineseClick(TextView tv, String meaning) {
        if (selectedEnglish == null) {
            Toast.makeText(this, "Select an English word first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a random distinct color for this pair
        int color = generateRandomColor();
        
        // Remove previous visual for this English word if it was already matched
        if (userMatches.containsKey(selectedEnglish)) {
            String oldChinese = userMatches.get(selectedEnglish);
            resetBoxColor(oldChinese, false);
        }
        
        // If this Chinese meaning was already matched to another English word, clear that match
        String englishToRemove = null;
        for (Map.Entry<String, String> entry : userMatches.entrySet()) {
            if (entry.getValue().equals(meaning)) {
                englishToRemove = entry.getKey();
                break;
            }
        }
        if (englishToRemove != null) {
            userMatches.remove(englishToRemove);
            resetBoxColor(englishToRemove, true);
        }

        // Store match and color
        userMatches.put(selectedEnglish, meaning);
        pairColors.put(selectedEnglish, color);
        
        // Apply color to both
        selectedEnglishView.setBackgroundColor(color);
        selectedEnglishView.setTextColor(Color.WHITE);
        tv.setBackgroundColor(color);
        tv.setTextColor(Color.WHITE);
        
        selectedEnglish = null;
        selectedEnglishView = null;
    }

    private int generateRandomColor() {
        Random rnd = new Random();
        // Generate a vibrant color (avoid too dark or too light)
        return Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
    }

    private void resetBoxColor(String text, boolean isEnglish) {
        List<TextView> list = isEnglish ? englishViews : chineseViews;
        for (TextView tv : list) {
            if (tv.getText().toString().equals(text)) {
                tv.setBackgroundResource(android.R.drawable.btn_default_small);
                tv.setTextColor(Color.BLACK);
            }
        }
    }

    private void validateResults() {
        if (userMatches.size() < quizList.size()) {
            Toast.makeText(this, "Please match all words first!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean allCorrect = true;
        Map<String, Boolean> results = new HashMap<>();

        for (Vocab v : quizList) {
            String userMeaning = userMatches.get(v.getEnglish());
            boolean isCorrect = v.getChinese().equals(userMeaning);
            results.put(v.getEnglish(), isCorrect);
            if (!isCorrect) allCorrect = false;
        }

        // Highlight results
        for (Vocab v : quizList) {
            String eng = v.getEnglish();
            String chi = userMatches.get(eng);
            boolean isCorrect = results.get(eng);
            
            highlightFinalBox(eng, isCorrect, true);
            highlightFinalBox(chi, isCorrect, false);
        }

        if (allCorrect) {
            Intent intent = new Intent(this, SuccessActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Some matches are wrong. Retrying...", Toast.LENGTH_SHORT).show();
            englishColumn.postDelayed(this::setupBoard, 1500);
        }
    }

    private void highlightFinalBox(String text, boolean correct, boolean isEnglish) {
        List<TextView> list = isEnglish ? englishViews : chineseViews;
        for (TextView tv : list) {
            if (tv.getText().toString().equals(text)) {
                tv.setBackgroundColor(correct ? Color.GREEN : Color.RED);
                tv.setTextColor(Color.WHITE);
            }
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