package com.example.vocab_master_mad_project;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomizeActivity extends AppCompatActivity {

    private VocabManager vocabManager;
    private RecyclerView recyclerView;
    private VocabAdapter adapter;
    private String currentFilter = "Basic";
    private TextView tvCurrentDifficulty;
    private LinearLayout buttonContainer;
    private MaterialButton btnCreateGroup;
    private MaterialButton btnAddNew;
    private MaterialButton btnDeleteGroup;
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        vocabManager = VocabManager.getInstance(this);

        // Music setup
        mediaPlayer = MediaPlayer.create(this, R.raw.difficulty_choose);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        // UI Setup
        tvCurrentDifficulty = findViewById(R.id.tv_current_difficulty);
        buttonContainer = findViewById(R.id.ll_button_container);
        btnCreateGroup = findViewById(R.id.btn_create_group);
        btnAddNew = findViewById(R.id.btn_add_new);
        btnDeleteGroup = findViewById(R.id.btn_delete_group);
        
        recyclerView = findViewById(R.id.rv_vocab_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new VocabAdapter(new ArrayList<>(), this::deleteVocab);
        recyclerView.setAdapter(adapter);

        // Standard Filter Buttons
        findViewById(R.id.btn_manage_basic).setOnClickListener(v -> loadVocabs("Basic"));
        findViewById(R.id.btn_manage_enhanced).setOnClickListener(v -> loadVocabs("Enhanced"));
        findViewById(R.id.btn_manage_elite).setOnClickListener(v -> loadVocabs("Elite"));
        
        // Add New Button
        btnAddNew.setOnClickListener(v -> showAddVocabDialog());

        // Create Group Button
        btnCreateGroup.setOnClickListener(v -> showCreateGroupDialog());

        // Delete Group Button
        btnDeleteGroup.setOnClickListener(v -> confirmDeleteGroup());

        // Mute Button
        MaterialButton btnMute = findViewById(R.id.btn_mute);
        btnMute.setOnClickListener(v -> {
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
        });

        // Load custom group buttons
        refreshGroupButtons();

        // Initial Load
        loadVocabs("Basic");
    }

    private void refreshGroupButtons() {
        // Find existing custom buttons (skip built-in 3 and the +NewGroup button)
        Set<String> builtIn = new HashSet<>();
        builtIn.add("Basic");
        builtIn.add("Enhanced");
        builtIn.add("Elite");

        // Remove all children except the fixed ones
        // In a real app we'd track these, but for simplicity:
        List<View> toRemove = new ArrayList<>();
        for (int i = 0; i < buttonContainer.getChildCount(); i++) {
            View child = buttonContainer.getChildAt(i);
            if (child.getId() != R.id.btn_manage_basic && 
                child.getId() != R.id.btn_manage_enhanced && 
                child.getId() != R.id.btn_manage_elite && 
                child.getId() != R.id.btn_create_group) {
                toRemove.add(child);
            }
        }
        for (View v : toRemove) buttonContainer.removeView(v);

        // Re-add from DB
        List<String> groups = vocabManager.getAllGroups();
        for (String group : groups) {
            if (!builtIn.contains(group)) {
                addDynamicGroupButton(group);
            }
        }
    }

    private void addDynamicGroupButton(String groupName) {
        MaterialButton newBtn = new MaterialButton(this, null, com.google.android.material.R.attr.materialButtonStyle);
        float density = getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) (100 * density),
                (int) (48 * density)
        );
        params.setMargins(0, 0, (int) (8 * density), 0);
        newBtn.setLayoutParams(params);
        newBtn.setText(groupName);
        newBtn.setAllCaps(false);
        newBtn.setMaxLines(1);
        newBtn.setCornerRadius((int) (20 * density));
        newBtn.setBackgroundResource(R.drawable.button_default);
        newBtn.setBackgroundTintList(null);
        newBtn.setPadding(0, 0, 0, 0);
        newBtn.setInsetTop(0);
        newBtn.setInsetBottom(0);

        newBtn.setOnClickListener(v -> loadVocabs(groupName));

        // Insert before the "+ New Group" button
        int insertIndex = buttonContainer.indexOfChild(btnCreateGroup);
        buttonContainer.addView(newBtn, insertIndex);
    }

    private void confirmDeleteGroup() {
        if (currentFilter.equals("Basic") || currentFilter.equals("Enhanced") || currentFilter.equals("Elite")) {
            Toast.makeText(this, "Default groups cannot be deleted", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
            .setTitle("Delete Group")
            .setMessage("Confirm to delete the whole group? All vocabs in '" + currentFilter + "' will be lost.")
            .setPositiveButton("Yes", (dialog, which) -> {
                vocabManager.deleteGroup(currentFilter);
                refreshGroupButtons();
                loadVocabs("Basic"); // Default back to Basic
                Toast.makeText(this, "Group deleted", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("No", null)
            .show();
    }

    private void showCreateGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New Group");

        final EditText input = new EditText(this);
        input.setHint("Group Name (e.g. Food)");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String groupName = input.getText().toString().trim();
            if (!groupName.isEmpty()) {
                addDynamicGroupButton(groupName);
                loadVocabs(groupName); // Switch to the new empty group
                Toast.makeText(this, "Group '" + groupName + "' created", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void loadVocabs(String difficulty) {
        currentFilter = difficulty;
        tvCurrentDifficulty.setText(difficulty + " List");
        List<Vocab> list = vocabManager.getSortedVocabs(difficulty);
        adapter.updateData(list);
    }

    private void deleteVocab(Vocab vocab) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Vocabulary")
            .setMessage("Are you sure you want to delete '" + vocab.getEnglish() + "'?")
            .setPositiveButton("Delete", (dialog, which) -> {
                vocabManager.deleteVocab(vocab);
                loadVocabs(currentFilter);
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showAddVocabDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Vocabulary");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_vocab, null);
        final EditText inputEnglish = viewInflated.findViewById(R.id.et_english);
        final EditText inputPos = viewInflated.findViewById(R.id.et_pos);
        final EditText inputChinese = viewInflated.findViewById(R.id.et_chinese);
        
        builder.setView(viewInflated);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String english = inputEnglish.getText().toString().trim();
            String pos = inputPos.getText().toString().trim();
            String chinese = inputChinese.getText().toString().trim();

            if (!english.isEmpty() && !pos.isEmpty() && !chinese.isEmpty()) {
                vocabManager.addVocab(new Vocab(english, pos, chinese, currentFilter));
                loadVocabs(currentFilter);
                Toast.makeText(this, "Added to " + currentFilter, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
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