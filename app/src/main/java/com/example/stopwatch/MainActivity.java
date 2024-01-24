package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView displayStopwatch;
    private Toolbar toolbar;
    private Button startButton, catchButton;
    private MainActivityViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewInicjalization();
        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainViewModel.getTime().observe(this, times
                -> displayStopwatch.setText(String.valueOf(times.get(0).getSeconds())));
        setSupportActionBar(toolbar);
        setStartButton();
        setCatchButton();
    }

    private void viewInicjalization() {
        toolbar = findViewById(R.id.main_activity_toolbar);
        displayStopwatch = findViewById(R.id.textView_display_stopwatch);
        startButton = findViewById(R.id.start_button);
        catchButton = findViewById(R.id.catch_button);

    }

    private void setStartButton() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.stopWatchRunInterface.sedDoRunning(true);
                mainViewModel.stopWatchRunInterface.startStopWatchRunning();
            }
        });
    }

    private void setCatchButton() {
        catchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.stopWatchRunInterface.sedDoRunning(false);
            }
        });
    }

    @Override
    public void finish() {
        mainViewModel.stopWatchRunInterface.shutDownExecutors();
        super.finish();
    }

}