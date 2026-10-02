package com.example.vocab_master_mad_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.VocabViewHolder> {

    private List<Vocab> vocabs;
    private OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Vocab vocab);
    }

    public VocabAdapter(List<Vocab> vocabs, OnDeleteClickListener deleteClickListener) {
        this.vocabs = vocabs;
        this.deleteClickListener = deleteClickListener;
    }

    public void updateData(List<Vocab> newVocabs) {
        this.vocabs = newVocabs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VocabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vocab_manage, parent, false);
        return new VocabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabViewHolder holder, int position) {
        Vocab vocab = vocabs.get(position);
        holder.tvEnglish.setText(vocab.getEnglish());
        holder.tvDetails.setText(vocab.getPos() + " - " + vocab.getChinese());
        
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(vocab);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vocabs.size();
    }

    static class VocabViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnglish, tvDetails;
        ImageButton btnDelete;

        public VocabViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEnglish = itemView.findViewById(R.id.tv_manage_english);
            tvDetails = itemView.findViewById(R.id.tv_manage_details);
            btnDelete = itemView.findViewById(R.id.btn_delete_vocab);
        }
    }
}