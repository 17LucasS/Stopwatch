package com.example.stopwatch.stopWatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stopwatch.R;

import java.util.List;
import java.util.Objects;

public class AdapterRecyclerStopWatch extends RecyclerView.Adapter<AdapterRecyclerStopWatch.ViewHolder> {
    private MutableLiveData<List<StopWatchCatchTimeTable>> list = new MutableLiveData<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_stop_watch_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    public void setList(MutableLiveData<List<StopWatchCatchTimeTable>> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position.setText(String.valueOf(position + 1));
        holder.time.setText(Objects.requireNonNull(list.getValue()).get(position).getTime());
        holder.lost.setText(list.getValue().get(position).getLostTime());
    }

    @Override
    public int getItemCount() {
        if (list.getValue() != null) {
            return list.getValue().size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView position;
        TextView time;
        TextView lost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.text_position);
            time = itemView.findViewById(R.id.text_time);
            lost = itemView.findViewById(R.id.text_lost_time);
        }
    }
}
