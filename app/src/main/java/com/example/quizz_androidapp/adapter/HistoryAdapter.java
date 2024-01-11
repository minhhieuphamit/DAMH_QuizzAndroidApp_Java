package com.example.quizz_androidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.data.model.result.Result;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<Result> historyList;

    public HistoryAdapter(Context context, List<Result> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result historyItem = historyList.get(position);

        // Set data to views
        holder.tvDate.setText(historyItem.getTimeEnd());
        holder.tvSroce.setText(String.valueOf(historyItem.getScore()));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSroce, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSroce = itemView.findViewById(R.id.tv_score_history);
            tvDate = itemView.findViewById(R.id.tv_date_history);
        }
    }
}
