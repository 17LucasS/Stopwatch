package com.example.stopwatch.MainFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stopwatch.MainActivity.ListenerMainInterface;
import com.example.stopwatch.R;
import com.example.stopwatch.MainActivity.StopWatchCategory;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerviewMainFragment extends RecyclerView.Adapter<AdapterRecyclerviewMainFragment.ViewHolder> {
    List<StopWatchCategory> list = new ArrayList<>();
    private final ListenerMainInterface listenerMainInterface;

    public AdapterRecyclerviewMainFragment(ListenerMainInterface listenerMainInterface) {
        this.listenerMainInterface = listenerMainInterface;
    }

    public void setList(List<StopWatchCategory> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_main, parent, false);
        return new ViewHolder(view, listenerMainInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImgResourceId());
        holder.textView.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView, ListenerMainInterface listenerMainInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(v -> listenerMainInterface.witchFragmentChoose(getAdapterPosition()));
        }

    }

}
