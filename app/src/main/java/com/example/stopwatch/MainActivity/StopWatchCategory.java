package com.example.stopwatch.MainActivity;

import android.content.Context;

import com.example.stopwatch.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class StopWatchCategory {

    private String title;
    private int imgResourceId;
    WeakReference<Context> weakReferenceContext;

    public StopWatchCategory() {

    }

    public StopWatchCategory(String title, int imgResourceId) {
        this.title = title;
        this.imgResourceId = imgResourceId;
    }

    public void getWeakReferenceContext(WeakReference<Context> contextWeakReference) {
        this.weakReferenceContext = contextWeakReference;
    }

    public List<StopWatchCategory> getListCategory() {
        List<StopWatchCategory> list = new ArrayList<>();
        list.add(new StopWatchCategory(weakReferenceContext.get()
                .getString(R.string.stoper_title), R.drawable.stopwach_time_24));
        list.add(new StopWatchCategory(weakReferenceContext.get()
                .getString(R.string.stoper_laps_title), R.drawable.laps_24));
        list.add(new StopWatchCategory(weakReferenceContext.get()
                .getString(R.string.stoper_feature_title), R.drawable.feature_24));
        list.add(new StopWatchCategory(weakReferenceContext.get()
                .getString(R.string.stoper_title), R.drawable.stopwach_time_24));
        list.add(new StopWatchCategory(weakReferenceContext.get()
                .getString(R.string.stoper_laps_title), R.drawable.laps_24));
        list.add(new StopWatchCategory(weakReferenceContext.get()
                .getString(R.string.stoper_feature_title), R.drawable.feature_24));
        return list;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public void setImgResourceId(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }
}
