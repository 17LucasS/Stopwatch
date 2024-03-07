package com.example.stopwatch.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.stopwatch.MainFragment.MainFragment;
import com.example.stopwatch.R;
import com.example.stopwatch.stopWatch.StopWatchFragment;
import com.example.stopwatch.stopWatchLaps.StopWatchLapsFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListenerMainInterface {

    public static String FRAGMENT = "fragment";
    private MainActivityViewModel mvvM;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        setFragment(savedInstanceState);

    }

    private void setFragment(Bundle savedInstanceState) {
        FrameLayout containerFragments = findViewById(R.id.container_fragments);
        if (savedInstanceState != null) {
            String strFragment = savedInstanceState.getString(FRAGMENT);
            Fragment visibleFragment = getSupportFragmentManager().findFragmentByTag(strFragment);
            if (visibleFragment != null) {
                getSupportFragmentManager().beginTransaction().show(visibleFragment).commit();
            }
        } else {
            setMainFragments();
        }
    }

    private void setMainFragments() {
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragments, mainFragment);
        transaction.commit();
    }

    private void setStopWatchFragments() {
        StopWatchFragment stopWatchFragment = new StopWatchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragments, stopWatchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setStopWatchLapsFragments() {
        StopWatchLapsFragment stopWatchLapsFragment = new StopWatchLapsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragments, stopWatchLapsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void witchFragmentChoose(int position) {
        Fragment fragment = null;
        if (position == 0) {
            setStopWatchFragments();
        }
        if (position == 1) {
            setStopWatchLapsFragments();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fr : fragments) {
            if (fr.isVisible()) {
                outState.putString(FRAGMENT, fr.getTag());
                return;
            }
        }
    }

    @Override
    public void finish() {
        mvvM.shutDownStopWatchExecutors();
        mvvM.shutdownExecutorDataBase();
        super.finish();
    }

}