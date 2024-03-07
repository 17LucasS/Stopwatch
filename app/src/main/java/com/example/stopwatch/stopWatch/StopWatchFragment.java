package com.example.stopwatch.stopWatch;

import android.annotation.SuppressLint;
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

import java.util.List;

public class StopWatchFragment extends Fragment {
    private TextView stopWatchTime;
    private Button startButton, catchButton;
    private StopWatchMvvM mvvM;
    private long timeMillisecond, saveMill;
    private AdapterRecyclerStopWatch adapter;
    private RecyclerView recyclerView;
    private MutableLiveData<List<StopWatchCatchTimeTable>> mutableLiveData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop_watch, container, false);
        stopWatchTime = view.findViewById(R.id.stop_watch_time_text_view);
        startButton = view.findViewById(R.id.start_button);
        catchButton = view.findViewById(R.id.catch_button);
        adapter = new AdapterRecyclerStopWatch();
        recyclerView = view.findViewById(R.id.recyclerView_stopWatch);
        mutableLiveData = new MutableLiveData<>();
        mvvM = new ViewModelProvider(this).get(StopWatchMvvM.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStartButtonListener();
        setCatchButtonListener();
        mvvM.setCatchRunning(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mvvM.checkIsServiceIsShutDownDataBase();
        setObserverDataTable();
        setObserverTimeLiveData();
        setObserverCatchTime();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void setObserverCatchTime() {
        mvvM.getAllNoteTimeStopWatch().observe(getViewLifecycleOwner(), stopWatchTimeTables -> {
            mutableLiveData.setValue(stopWatchTimeTables);
            adapter.setList(mutableLiveData);
            if (mutableLiveData.getValue() != null && !mutableLiveData.getValue().isEmpty()) {
                adapter.notifyItemChanged(mutableLiveData.getValue().size() - 1);
            } else {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setObserverTimeLiveData() {
        mvvM.getTimeLiveData().observe(getViewLifecycleOwner(), s -> stopWatchTime.setText(s));
    }

    private void setObserverDataTable() {
        mvvM.getAllNoteStopWatchData().observe(getViewLifecycleOwner(), stopWatchDataTables -> {
            if (!stopWatchDataTables.isEmpty()) {
                stopWatchTime.setText(stopWatchDataTables.get(0).getTime());
                startButton.setText(stopWatchDataTables.get(0).getStartButton());
                catchButton.setText(stopWatchDataTables.get(0).getCatchButton());
                catchButton.setEnabled(stopWatchDataTables.get(0).isCatchEnable());
                timeMillisecond = stopWatchDataTables.get(0).getMillisecond();
                saveMill = stopWatchDataTables.get(0).getSaveMilli();
                if (stopWatchDataTables.get(0).isTimeRunning()) {
                    timeMillisecond = stopWatchDataTables.get(0).getMillisecondWhenRun();
                    mvvM.setupStopWatchRunWhenAppWasRun(timeMillisecond);
                }
            }
        });
    }

    private void setCatchButtonListener() {
        catchButton.setOnClickListener(v -> {
            String catchTextButton = catchButton.getText().toString();
            String catchTime;
            String recordTime;
            if (catchTextButton.equals(getString(R.string.catch_string))) {
                catchTime = stopWatchTime.getText().toString();
                if (adapter.getItemCount() == 0) {
                    saveMill = mvvM.getTimeMillisecond();
                    recordTime = getString(R.string.empty_records);
                } else {
                    long millisecond = mvvM.getTimeMillisecond() - saveMill;
                    recordTime = mvvM.returnDifferencesTime(System.currentTimeMillis() - millisecond);
                    saveMill = mvvM.getTimeMillisecond();
                }
                mvvM.insertTimeStopWatch(new StopWatchCatchTimeTable(catchTime, recordTime));
                return;
            }

            if (catchTextButton.equals(getString(R.string.reset_string))) {
                mvvM.deleteAllTimeStopWatch();
                setDefaultTextForViewAndVariables();
            }

        });
    }

    private void setStartButtonListener() {
        startButton.setOnClickListener(v -> {
            String startB = startButton.getText().toString();
            if (startB.equals(getString(R.string.start_string))) {
                catchButton.setEnabled(true);
                startButton.setText(getString(R.string.stop_string));
                mvvM.setStartButton();
            }
            if (startB.equals(getString(R.string.stop_string))) {
                startButton.setText(getString(R.string.resume_string));
                catchButton.setText(getString(R.string.reset_string));
                timeMillisecond = mvvM.getTimeMillisecond();
                mvvM.setStopButton();
            }
            if (startB.equals(getString(R.string.resume_string))) {
                startButton.setText(getString(R.string.stop_string));
                timeMillisecond = System.currentTimeMillis() - timeMillisecond;
                mvvM.setResumeButton(timeMillisecond);
            }
        });
    }

    private void setDefaultTextForViewAndVariables() {
        mvvM.setTimeRunning(false);
        startButton.setText(getString(R.string.start_string));
        catchButton.setText(getString(R.string.catch_string));
        catchButton.setEnabled(false);
        stopWatchTime.setText(getString(R.string.default_settings_text_stopwatch));
        saveMill = 0;
    }

    @Override
    public void onStop() {
        super.onStop();
        saveData();
    }

    private void saveData() {
        String startText = startButton.getText().toString();
        String catchText = catchButton.getText().toString();
        String timeText = stopWatchTime.getText().toString();
        boolean timeRunning = mvvM.getTimeRunning();
        long timeMillisecond = mvvM.getTimeMillisecond();
        long saveM = saveMill;
        boolean catchIsEnable = catchButton.isEnabled();
        long millisecondWhenRun = mvvM.getUserMillisecond();
        StopWatchDataTable stopWatchDataTable = new StopWatchDataTable
                (timeText, startText, catchText, timeRunning, timeMillisecond, saveM, millisecondWhenRun, catchIsEnable);
        mvvM.saveData(stopWatchDataTable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvvM.shutDownStopWatchExecutors();
        mvvM.shutdownExecutorDataBase();
    }
}