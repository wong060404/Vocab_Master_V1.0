package com.example.vocab_master_mad_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private List<String> groups;
    private OnGroupClickListener listener;

    public interface OnGroupClickListener {
        void onGroupClick(String groupName);
    }

    public GroupAdapter(List<String> groups, OnGroupClickListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_select, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        String name = groups.get(position);
        holder.tvName.setText(name);
        holder.itemView.setOnClickListener(v -> listener.onGroupClick(name));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_group_name);
        }
    }
}