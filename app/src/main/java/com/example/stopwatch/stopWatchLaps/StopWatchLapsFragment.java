package com.example.stopwatch.stopWatchLaps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.stopwatch.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.List;

public class StopWatchLapsFragment extends Fragment {

    private TextView timeStopWatch, catchTime, bestTime;
    private Button startButton, catchButton;
    private StopWatchLapsMvvM watchLapsMvvM;
    private long timeMillisecond, catchTimeMillisecond;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterStopWatchLaps adapter;
    private WeakReference<Context> weakReferenceContext;
    private MutableLiveData<List<StopWatchLapsCatchTable>> mutableLiveData;
    private FloatingActionButton floatingActionButton;
    private boolean firstTimeRun;
    private long saveMill;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.weakReferenceContext = new WeakReference<>(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stop_watch_laps, container, false);
        floatingActionButton = view.findViewById(R.id.floatingActionButtonLaps);
        timeStopWatch = view.findViewById(R.id.stop_watch_time_text_view);
        catchTime = view.findViewById(R.id.catch_time_text_view);
        bestTime = view.findViewById(R.id.textView_best_time_display);
        startButton = view.findViewById(R.id.start_button);
        catchButton = view.findViewById(R.id.catch_button);
        mutableLiveData = new MutableLiveData<>();
        watchLapsMvvM = new ViewModelProvider(this).get(StopWatchLapsMvvM.class);
        recyclerView = view.findViewById(R.id.recyclerView_stopWatchLaps);
        adapter = new RecyclerViewAdapterStopWatchLaps(weakReferenceContext);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        watchLapsMvvM.checkIsServiceIsShutDownDataBase();
        setStartButton();
        setCatchButton();
        setAdapterRecyclerView();
        setStopWatchLapsDataObserver();
        setCatchListObserver();
        setTimeObserver();
        setCatchTime();
    }

    private void setTimeObserver() {
        watchLapsMvvM.getTimeLiveData().observe(getViewLifecycleOwner(), times -> {
            if (firstTimeRun) {
                firstTimeRun = false;
                return;
            }
            timeStopWatch.setText(times);
        });

    }

    private void setCatchTime() {
        watchLapsMvvM.getCatchTimeLiveData().observe(getViewLifecycleOwner(), s -> catchTime.setText(s));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setCatchListObserver() {
        watchLapsMvvM.getLiveDataStopWatchLapsCatch().observe(getViewLifecycleOwner(), stopWatchLapsCatchTables -> {
            mutableLiveData.setValue(stopWatchLapsCatchTables);
            adapter.setList(mutableLiveData.getValue());
            if (mutableLiveData.getValue() != null && !mutableLiveData.getValue().isEmpty()) {
                adapter.notifyItemInserted(mutableLiveData.getValue().size() - 1);
            } else {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setStopWatchLapsDataObserver() {
        watchLapsMvvM.getLiveDataStopWatchLapsData().observe(getViewLifecycleOwner(), stopWatchLapsDataTableList -> {
            if (stopWatchLapsDataTableList != null && !stopWatchLapsDataTableList.isEmpty()) {
                StopWatchLapsDataTable table = stopWatchLapsDataTableList.get(stopWatchLapsDataTableList.size() - 1);
                timeStopWatch.setText(table.getTime());
                startButton.setText(table.getStartButton());
                catchButton.setText(table.getCatchButt());
                catchButton.setEnabled(table.isCatchTimeIsEnable());
//                bestTime.setText(table.getBestTime());
                timeMillisecond = table.getTimeMillisecond();
                catchTimeMillisecond = table.getCatchMillisecond();
                saveMill = table.getSaveMill();
                catchTime.setText(table.getCatchTime());
                watchLapsMvvM.setupStopWatchRunWhenAppWasRun(table.isTimeRunning(), table.getUserMillisecond());
                watchLapsMvvM.setupStopWatchRunWhenAppWasRunCatch(table.isCatchRunning(), table.getCatchMillisecond(), table.getCatchUserMillisecond());

            }
        });
    }

    private void setAdapterRecyclerView() {
        mutableLiveData.setValue(watchLapsMvvM.getLiveDataStopWatchLapsCatch().getValue());
        adapter.setList(mutableLiveData.getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setStartButton() {
        startButton.setOnClickListener(v -> {
            String start = startButton.getText().toString();
            if (start.equals(getString(R.string.start_string))) {
                startButton.setText(getString(R.string.stop_string));
                catchButton.setEnabled(true);
                watchLapsMvvM.startStopWatchRun();
                return;
            }
            if (start.equals(getString(R.string.stop_string))) {
                watchLapsMvvM.setTimeRunning(false);
                watchLapsMvvM.setCatchRunning(false);
                catchButton.setText(getString(R.string.reset_string));
                startButton.setText(getString(R.string.resume_string));
                timeMillisecond = watchLapsMvvM.getTimeMillisecond();
                catchTimeMillisecond = watchLapsMvvM.getCatchMillisecond();
                return;
            }
            if (start.equals(getString(R.string.resume_string))) {
                startButton.setText(getString(R.string.stop_string));
                catchButton.setText(getString(R.string.catch_string));
                timeMillisecond = System.currentTimeMillis() - timeMillisecond;
                watchLapsMvvM.resumeStopWatchRun(timeMillisecond);
                boolean catchTimeWasActive = !catchTime.getText().toString().equals(getString(
                        R.string.default_settings_text_stopwatch));
                watchLapsMvvM.catchTimeWasActive(catchTimeWasActive, catchTimeMillisecond);
            }
        });
    }

    private void setCatchButton() {
        catchButton.setOnClickListener(v -> {
            String catchB = catchButton.getText().toString();

            if (catchB.equals(getString(R.string.reset_string))) {
                watchLapsMvvM.resetStopWatchRun();
                setDefaultTextForViewAndVariables();
                return;
            }
            if (catchB.equals(getString(R.string.catch_string))) {
                String insertTime;
                String differencesT;
                long diff;
                String overallTime = timeStopWatch.getText().toString();
                if (adapter.getItemCount() == 0) {
                    saveMill = watchLapsMvvM.getTimeMillisecond();
                    insertTime = timeStopWatch.getText().toString();
                    bestTime.setText(insertTime);
                    differencesT = getString(R.string.empty_records);
                } else {
                    long time = watchLapsMvvM.getCatchMillisecond();
                    insertTime = catchTime.getText().toString();

                    if (time < saveMill) {
                        bestTime.setText(catchTime.getText());
                        diff = time - saveMill;
                        saveMill = time;
                    } else {
                        diff = time - saveMill;
                    }
                    differencesT = watchLapsMvvM.returnDifferencesTime(System.currentTimeMillis() - diff);
                }
                watchLapsMvvM.insertStopWatchLapsCatch(new StopWatchLapsCatchTable(
                        (adapter.getItemCount() + 1) + ": " + insertTime, differencesT, overallTime));
                watchLapsMvvM.setCatchUserMillisecond(System.currentTimeMillis());
                watchLapsMvvM.setCatchRunning(true);
            }
        });
    }

    private void setDefaultTextForViewAndVariables() {
        watchLapsMvvM.setTimeRunning(false);
        startButton.setText(getString(R.string.start_string));
        catchButton.setText(getString(R.string.catch_string));
        catchButton.setEnabled(false);
        timeStopWatch.setText(getString(R.string.default_settings_text_stopwatch));
        bestTime.setText(R.string.default_settings_text_stopwatch);
        catchTime.setText(R.string.default_settings_text_stopwatch);
        watchLapsMvvM.deleteStopWatchLapsData();
        watchLapsMvvM.deleteStopWatchLapsCatch();
    }


    @Override
    public void onStop() {
        super.onStop();
        saveData();

    }

    private void saveData() {
        boolean doRun = watchLapsMvvM.getTimeRunning();
        String startB = startButton.getText().toString();
        String catchB = catchButton.getText().toString();
        String time = timeStopWatch.getText().toString();
        String beTime = bestTime.getText().toString();
        boolean catchBEnable = catchButton.isEnabled();
        long millSecond = watchLapsMvvM.getTimeMillisecond();
        long userMillisecond = watchLapsMvvM.getUserMillisecond();
        String catchT = catchTime.getText().toString();
        boolean catchRunning = watchLapsMvvM.getCatchRunning();
        long catchMill = watchLapsMvvM.getCatchMillisecond();
        long catchUserMill = watchLapsMvvM.getCatchUserMillisecond();
        StopWatchLapsDataTable table = new StopWatchLapsDataTable(time, beTime, startB, catchB, doRun, catchBEnable, millSecond, userMillisecond, catchT, catchRunning, catchMill, catchUserMill, saveMill);
        watchLapsMvvM.insertDataStopWatchLaps(table);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        watchLapsMvvM.shutDownStopWatchExecutors();
        watchLapsMvvM.shutdownExecutorDataBase();
    }
}