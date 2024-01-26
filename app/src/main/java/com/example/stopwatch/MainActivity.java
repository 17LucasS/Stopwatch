package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView displayStopwatch;
    private Toolbar toolbar;
    private Button startButton, catchButton;
    private MainActivityViewModel mainViewModel;
    private long milliSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        milliSecond = 0;
        viewInitialization();
        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainViewModel.getTime().observe(this, times -> displayStopwatch.setText(times));

        if (savedInstanceState != null) {
            loadDataFromSharedPreference();
        }
        setStartButton();
        setCatchButton();
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
        displayStopwatch.setText(time);
        milliSecond = mainViewModel.sharedPreferenceGetMillisecond();
        if (doRun){
            mainViewModel.stopWatchRunInterface.setMillisecond(System.currentTimeMillis()-milliSecond);
            mainViewModel.stopWatchRunInterface.startStopWatchRunning();
            mainViewModel.stopWatchRunInterface.sedDoRunning(true);
        }
    }

    private void viewInitialization() {
        toolbar = findViewById(R.id.main_activity_toolbar);
        displayStopwatch = findViewById(R.id.textView_display_stopwatch);
        startButton = findViewById(R.id.start_button);
        catchButton = findViewById(R.id.catch_button);
        catchButton.setEnabled(false);

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
            if (catchB.equals(getString(R.string.reset_string))){
                startButton.setText(getString(R.string.start_string));
                catchButton.setText(getString(R.string.catch_string));
                catchButton.setEnabled(false);
                displayStopwatch.setText(getString(R.string.default_settings_text_stopwatch));
            }


        });
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
        String time = displayStopwatch.getText().toString();
        boolean doRun = mainViewModel.isDoRunning();
        boolean catchBEnable = catchButton.isEnabled();
        long millSecond = mainViewModel.getTMillisecond();
        mainViewModel.savaData(startB, catchB, time, doRun,catchBEnable, millSecond);
        super.onStop();
    }

    @Override
    public void finish() {
        mainViewModel.stopWatchRunInterface.shutDownExecutors();
        super.finish();
    }

}