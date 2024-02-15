package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView timeStopWatch, catchTime, bestTime;
    private Button startButton, catchButton;
    private MainActivityViewModel mainViewModel;
    private long timeMillisecond, catchTimeMillisecond;
    private RecyclerView recyclerView;
    private MainAdapterRecyclerView mainAdapter;
    private MutableLiveData<List<CatchTimeTable>> mutableLiveData;
    private FloatingActionButton floatingActionButton;
    private boolean firstTimeRun;
    private long saveMill;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewInitialization();
        setAdapterRecyclerView();
        setMainDataObserver();
        setTimeObserver();
        setCatchTimeObserver();
        setCatchListObserver();
        setStartButton();
        setCatchButton();
        setFloatingActionButton();
    }

    private void setCatchTimeObserver() {
        mainViewModel.getCatchTimeLiveData().observe(this, s -> catchTime.setText(s));
    }

    private void setMainDataObserver() {
        mainViewModel.getMainDataList().observe(this, mainActDataTables -> {
            if (mainActDataTables != null && !mainActDataTables.isEmpty()) {
                MainActDataTable table = mainActDataTables.get(mainActDataTables.size() - 1);
                timeStopWatch.setText(table.getTime());
                startButton.setText(table.getStartButton());
                catchButton.setText(table.getCatchButt());
                catchButton.setEnabled(table.isCatchTimeIsEnable());
                bestTime.setText(table.getBestTime());
                timeMillisecond = table.getTimeMillisecond();
                catchTimeMillisecond = table.getCatchMillisecond();
                saveMill = table.getSaveMill();
                catchTime.setText(table.getCatchTime());
                mainViewModel.setupStopWatchWhenAppStartRun(table.isTimeRunning(), table.getUserMillisecond());

                mainViewModel.setupStopWatchWhenAppStartRunCatch(table.isCatchRunning(), table.getCatchMillisecond(), table.getCatchUserMillisecond());

            }
        });
    }

    private void setTimeObserver() {
        mainViewModel.getTimeLiveData().observe(this, times -> {
            if (firstTimeRun) {
                firstTimeRun = false;
                return;
            }
            timeStopWatch.setText(times);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setCatchListObserver() {
        mainViewModel.getCatchList().observe(this, noteMeasurements -> {
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
        mutableLiveData.setValue(mainViewModel.getCatchList().getValue());
        mainAdapter.setList(mutableLiveData.getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);
    }

    private void viewInitialization() {
        Context context = this;
         WeakReference<Context> contextWeakReference = new WeakReference<>(context);

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
        mainAdapter = new MainAdapterRecyclerView(contextWeakReference);
        firstTimeRun = true;
    }

    private void setFloatingActionButton() {
        floatingActionButton.setOnClickListener(v -> Toast.makeText(this, "SAVE", Toast.LENGTH_SHORT).show());
    }

    private void setStartButton() {
        startButton.setOnClickListener(v -> {
            String start = startButton.getText().toString();

            if (start.equals(getString(R.string.start_string))) {
                startButton.setText(getString(R.string.stop_string));
                catchButton.setEnabled(true);
                mainViewModel.startStopWatchRun();
            } else if (start.equals(getString(R.string.stop_string))) {
                mainViewModel.setTimeRunning(false);
                mainViewModel.setCatchRunning(false);
                catchButton.setText(getString(R.string.reset_string));
                startButton.setText(getString(R.string.resume_string));
                timeMillisecond = mainViewModel.getTimeMillisecond();
                catchTimeMillisecond = mainViewModel.getCatchMillisecond();
            } else if (start.equals(getString(R.string.resume_string))) {
                startButton.setText(getString(R.string.stop_string));
                catchButton.setText(getString(R.string.catch_string));
                timeMillisecond = System.currentTimeMillis() - timeMillisecond;
                mainViewModel.resumeStopWatchRun(timeMillisecond);

                boolean catchTimeWasActive = !catchTime.getText().toString().equals(getString(
                        R.string.default_settings_text_stopwatch));
                mainViewModel.catchTimeWasActive(catchTimeWasActive, catchTimeMillisecond);

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
                String differencesT;
                long diff;
                String overallTime = timeStopWatch.getText().toString();
                if (mainAdapter.getItemCount() == 0) {
                    saveMill = mainViewModel.getTimeMillisecond();
                    insertTime = timeStopWatch.getText().toString();
                    bestTime.setText(insertTime);
                    differencesT = "----";
                } else {
                    long time = mainViewModel.getCatchMillisecond();
                    insertTime = catchTime.getText().toString();

                    if (time < saveMill) {
                        bestTime.setText(catchTime.getText());
                         diff = time - saveMill;
                        saveMill = time;
                    }else {
                        diff = time - saveMill;
                    }
                    differencesT = mainViewModel.returnDifferencesTime(System.currentTimeMillis()-diff);
                }
                mainViewModel.insertCatch(new CatchTimeTable((mainAdapter.getItemCount()+1) +": "+insertTime,differencesT,overallTime));
                mainViewModel.setCatchUserMillisecond(System.currentTimeMillis());
                mainViewModel.setCatchRunning(true);
            }
        });
    }

    private void setDefaultTextForViewAndVariables() {
        mainViewModel.setTimeRunning(false);
        startButton.setText(getString(R.string.start_string));
        catchButton.setText(getString(R.string.catch_string));
        catchButton.setEnabled(false);
        timeStopWatch.setText(getString(R.string.default_settings_text_stopwatch));
        bestTime.setText(R.string.default_settings_text_stopwatch);
        catchTime.setText(R.string.default_settings_text_stopwatch);
        mainViewModel.deleteCatch();

    }


    @Override
    protected void onStop() {
        super.onStop();
        boolean doRun = mainViewModel.getTimeRunning();
        String startB = startButton.getText().toString();
        String catchB = catchButton.getText().toString();
        String time = timeStopWatch.getText().toString();
        String beTime = bestTime.getText().toString();
        boolean catchBEnable = catchButton.isEnabled();
        long millSecond = mainViewModel.getTimeMillisecond();
        long userMillisecond = mainViewModel.getUserMillisecond();

        String catchT = catchTime.getText().toString();
        boolean catchRunning = mainViewModel.getCatchRunning();
        long catchMill = mainViewModel.getCatchMillisecond();
        long catchUserMill = mainViewModel.getCatchUserMillisecond();
        MainActDataTable mainTable = new MainActDataTable(time, beTime, startB, catchB, doRun, catchBEnable, millSecond, userMillisecond, catchT, catchRunning, catchMill, catchUserMill, saveMill);
        mainViewModel.insertMainData(mainTable);

    }

    @Override
    public void finish() {
        mainViewModel.shutDownStopWatchExecutors();
        mainViewModel.shutdownExecutorDataBase();
        super.finish();
    }

}