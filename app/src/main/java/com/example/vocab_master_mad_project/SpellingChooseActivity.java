package com.example.vocab_master_mad_project;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class SpellingChooseActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_choose);

        mediaPlayer = MediaPlayer.create(this, R.raw.difficulty_choose);
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

        setupButton(R.id.btn_spelling_basic, "Basic");
        setupButton(R.id.btn_spelling_enhanced, "Enhanced");
        setupButton(R.id.btn_spelling_elite, "Elite");

        Button btnCustomize = findViewById(R.id.btn_customize);
        btnCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomGroupsDialog();
            }
        });
    }

    private void showCustomGroupsDialog() {
        List<String> groups = VocabManager.getInstance(this).getAllGroups();
        List<String> customGroups = new ArrayList<>();
        for (String group : groups) {
            if (!group.equals("Basic") && !group.equals("Enhanced") && !group.equals("Elite")) {
                customGroups.add(group);
            }
        }

        if (customGroups.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Custom Groups")
                    .setMessage("No custom groups found. Create some in Customize mode!")
                    .setPositiveButton("OK", (dialog, which) -> {
                        Intent intent = new Intent(SpellingChooseActivity.this, CustomizeActivity.class);
                        startActivity(intent);
                    })
                    .show();
            return;
        }

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_group_chooser, null);
        RecyclerView rvGroups = dialogView.findViewById(R.id.rv_custom_groups);
        Button btnManage = dialogView.findViewById(R.id.btn_dialog_manage);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        rvGroups.setAdapter(new GroupAdapter(customGroups, groupName -> {
            Intent intent = new Intent(SpellingChooseActivity.this, LoadingActivity.class);
            intent.putExtra("DIFFICULTY", groupName);
            intent.putExtra("MODE", "SPELLING");
            startActivity(intent);
            dialog.dismiss();
        }));

        btnManage.setOnClickListener(v -> {
            Intent intent = new Intent(SpellingChooseActivity.this, CustomizeActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setupButton(int id, String difficulty) {
        Button btn = findViewById(id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Jump to LoadingActivity instead of StudyActivity
                Intent intent = new Intent(SpellingChooseActivity.this, LoadingActivity.class);
                intent.putExtra("DIFFICULTY", difficulty);
                intent.putExtra("MODE", "SPELLING");
                startActivity(intent);
            }
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