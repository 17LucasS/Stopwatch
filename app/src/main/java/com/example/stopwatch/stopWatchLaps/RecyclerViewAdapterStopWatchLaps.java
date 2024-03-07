package com.example.stopwatch.stopWatchLaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stopwatch.R;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapterStopWatchLaps extends RecyclerView.Adapter<RecyclerViewAdapterStopWatchLaps.ViewHolder> {

    private final MutableLiveData<List<StopWatchLapsCatchTable>> list = new MutableLiveData<>();
    WeakReference<Context> contextWeakReference;
    public RecyclerViewAdapterStopWatchLaps(WeakReference<Context> contextWeakReference){
        this.contextWeakReference = contextWeakReference;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.timeText.setText(Objects.requireNonNull(list.getValue()).get(position).getCatchTime());
        String differencesTime = list.getValue().get(position).getTimeDifferences();
        holder.differencesTime.setText(list.getValue().get(position).getTimeDifferences());
        holder.overallTime.setText(list.getValue().get(position).getOverallTime());
        if (differencesTime.charAt(0)=='-'){
            holder.differencesTime.setTextColor(contextWeakReference.get().getColor(R.color.green));
        }else if (differencesTime.charAt(0)=='+'){
            holder.differencesTime.setTextColor(contextWeakReference.get().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        if (list.getValue() != null) {
            return list.getValue().size();
        } else return 0;
    }

    public void setList(List<StopWatchLapsCatchTable> value) {
        list.setValue(value);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView timeText,differencesTime,overallTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.card_view_text_View);
            differencesTime = itemView.findViewById(R.id.card_view_straty);
            overallTime = itemView.findViewById(R.id.card_view_czas_ogolny);
        }
    }
}
