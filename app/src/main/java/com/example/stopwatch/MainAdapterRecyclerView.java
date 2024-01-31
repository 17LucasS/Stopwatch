package com.example.stopwatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class MainAdapterRecyclerView extends RecyclerView.Adapter<MainAdapterRecyclerView.ViewHolder> {

    private final MutableLiveData<List<NoteMeasurement>> list = new MutableLiveData<>();

    public void setList(List<NoteMeasurement> list) {
        this.list.setValue(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(Objects.requireNonNull(list.getValue()).get(position).getTime());
    }

    @Override
    public int getItemCount() {
        if (list.getValue() != null) {
            return list.getValue().size();
        } else return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.card_view_text_View);
        }
    }
}
