package com.example.stopwatch.MainFragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stopwatch.MainActivity.ListenerMainInterface;
import com.example.stopwatch.R;
import com.example.stopwatch.MainActivity.StopWatchCategory;

import java.lang.ref.WeakReference;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private WeakReference<Context> contextWeakReference;
    private ListenerMainInterface listenerMainInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewMainFragment);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contextWeakReference = new WeakReference<>(context);
        this.listenerMainInterface = (ListenerMainInterface) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdapterRecyclerviewMainFragment adapter = new AdapterRecyclerviewMainFragment(listenerMainInterface);
        StopWatchCategory stopWatchCategory = new StopWatchCategory();
        stopWatchCategory.getWeakReferenceContext(contextWeakReference);
        adapter.setList(stopWatchCategory.getListCategory());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int position = parent.getChildAdapterPosition(view);
                if (position < 3) { // dla pierwszego wiersza
                    outRect.top = convertDpToPx(); // margines w pikselach
                } else {
                    outRect.top = 0;
                }
            }

            private int convertDpToPx() {
                return Math.round(500 * (requireContext().getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
            }
        });
    }

}