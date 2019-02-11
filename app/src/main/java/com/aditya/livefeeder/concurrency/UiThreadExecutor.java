package com.aditya.livefeeder.concurrency;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;

public class UiThreadExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable command) {
        new Handler(Looper.getMainLooper()).post(command);
    }
}
