package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView timeStopWatch, catchTime, bestTime;
    private Button startButton, catchButton;
    private MainActivityViewModel mainViewModel;
    private long milliSecond;
    private RecyclerView recyclerView;
    private MainAdapterRecyclerView mainAdapter;
    private MutableLiveData<List<NoteMeasurement>> mutableLiveData;
    private FloatingActionButton floatingActionButton;
    private boolean firstTimeRun = true;
    private long saveMill;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewInitialization();
        setAdapterRecyclerView();

        setObserverTimes();
        setObserverGetAllMeasurements();

        mainViewModel.getSecondTimeList().observe(this, s -> catchTime.setText(s));

        loadDataFromSharedPreference();

        setStartButton();
        setCatchButton();
        setFloatingActionButton();
    }

    private void setObserverTimes() {
        mainViewModel.getTime().observe(this, times -> {
            if (firstTimeRun) {
                firstTimeRun = false;
                return;
            }
            timeStopWatch.setText(times);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setObserverGetAllMeasurements() {
        mainViewModel.getAllMeasurements().observe(this, noteMeasurements -> {
            mutableLiveData.setValue(noteMeasurements);
            mainAdapter.setList(mutableLiveData.getValue());

            if (mutableLiveData.getValue() != null && !mutableLiveData.getValue().isEmpty()) {
                mainAdapter.notifyItemInserted(mutableLiveData.getValue().size() - 1);
            } else {
                mainAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setAdapterRecyclerView() {
        mutableLiveData.setValue(mainViewModel.getAllMeasurements().getValue());
        mainAdapter.setList(mutableLiveData.getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);
    }

    private void loadDataFromSharedPreference() {
        String startB = mainViewModel.sharedPreferenceGetStarButton();
        String catchB = mainViewModel.sharedPreferenceGetCatchButton();
        String time = mainViewModel.sharedPreferenceGetTime();
        boolean doRun = mainViewModel.sharedPreferenceGetIsRunning();
        boolean catchBEnable = mainViewModel.sharedPreferenceGetCatchButtonIsEnable();
        startButton.setText(startB);
        catchButton.setText(catchB);
        catchButton.setEnabled(catchBEnable);
        timeStopWatch.setText(time);
        Log.v("LOAD", time);
        milliSecond = mainViewModel.sharedPreferenceGetMillisecond();
        if (doRun) {
            mainViewModel.stopWatchRunInterface.setMillisecond(System.currentTimeMillis() - milliSecond);
            mainViewModel.stopWatchRunInterface.sedDoRunning(true);
            mainViewModel.stopWatchRunInterface.startStopWatchRunning();
        }
    }

    private void viewInitialization() {
        milliSecond = 0;
        floatingActionButton = findViewById(R.id.main_floatingActionButton);
        recyclerView = findViewById(R.id.main_recyclerView);
        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        bestTime = findViewById(R.id.best_time);
        timeStopWatch = findViewById(R.id.textView_display_stopwatch);
        catchTime = findViewById(R.id.textView_second_time);
        startButton = findViewById(R.id.start_button);
        catchButton = findViewById(R.id.catch_button);
        catchButton.setEnabled(false);
        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mutableLiveData = new MutableLiveData<>();
        mainAdapter = new MainAdapterRecyclerView();
    }

    private void setFloatingActionButton() {
        floatingActionButton.setOnClickListener(v -> Toast.makeText(this, "SAVE", Toast.LENGTH_SHORT).show());
    }

    private void setStartButton() {
        startButton.setOnClickListener(v -> {
            String start = startButton.getText().toString();
            if (start.equals(getString(R.string.start_string))) {
                startStopWatchRun();
            } else if (start.equals(getString(R.string.stop_string))) {
                stopStopWatchRun();
            } else if (start.equals(getString(R.string.resume_string))) {
                resumeStopWatchRun();
            }
        });
    }

    private void setCatchButton() {
        catchButton.setOnClickListener(v -> {
            String catchB = catchButton.getText().toString();
            if (catchB.equals(getString(R.string.reset_string))) {
                mainViewModel.resetStopWatchRun();
                setDefaultTextForViewAndVariables();

            } else if (catchB.equals(getString(R.string.catch_string))) {
                String insertTime;
                if (mutableLiveData.getValue() == null) {
                    insertTime = timeStopWatch.getText().toString();
                } else {
                    insertTime = catchTime.getText().toString();
                }
                mainViewModel.measurementInterface.insertMeasurement(new NoteMeasurement(insertTime));
                mainViewModel.stopWatchRunInterface.setDoRunSecondMillisecond(true);
                mainViewModel.stopWatchRunInterface.setSecondMillisecond(System.currentTimeMillis());

                if (mainAdapter.getItemCount() == 0) {
                    saveMill = mainViewModel.getTMillisecond();
                    bestTime.setText(timeStopWatch.getText().toString());
                }else {
                    if (mainViewModel.getTSecondMill() < saveMill) {
                        saveMill = mainViewModel.getTSecondMill();
                        bestTime.setText(catchTime.getText());
                    }
                }


            }
        });
    }

    private void setDefaultTextForViewAndVariables() {
        startButton.setText(getString(R.string.start_string));
        catchButton.setText(getString(R.string.catch_string));
        catchButton.setEnabled(false);
        timeStopWatch.setText(getString(R.string.default_settings_text_stopwatch));
        bestTime.setText(R.string.default_settings_text_stopwatch);
        catchTime.setText(R.string.default_settings_text_stopwatch);
        mainViewModel.measurementInterface.deleteMeasurement();
    }

    private void startStopWatchRun() {
        startButton.setText(getString(R.string.stop_string));
        catchButton.setEnabled(true);
        mainViewModel.startStopWatchRun();
    }

    private void resumeStopWatchRun() {
        startButton.setText(getString(R.string.stop_string));
        catchButton.setText(getString(R.string.catch_string));
        milliSecond = System.currentTimeMillis() - milliSecond;
        mainViewModel.resumeStopWatchRun(milliSecond);
    }

    private void stopStopWatchRun() {
        catchButton.setText(getString(R.string.reset_string));
        startButton.setText(getString(R.string.resume_string));
        milliSecond = mainViewModel.getTMillisecond();
        mainViewModel.stopWatchRunInterface.sedDoRunning(false);
    }

    @Override
    protected void onStop() {
        String startB = startButton.getText().toString();
        String catchB = catchButton.getText().toString();
        String time = timeStopWatch.getText().toString();
        boolean doRun = mainViewModel.isDoRunning();
        boolean catchBEnable = catchButton.isEnabled();
        long millSecond = mainViewModel.getTMillisecond();
        mainViewModel.savaData(startB, catchB, time, doRun, catchBEnable, millSecond);
        super.onStop();
    }

    @Override
    public void finish() {
        mainViewModel.stopWatchRunInterface.shutDownExecutors();
        mainViewModel.measurementInterface.shutdownExecutorMeasurement();
        super.finish();
    }

}