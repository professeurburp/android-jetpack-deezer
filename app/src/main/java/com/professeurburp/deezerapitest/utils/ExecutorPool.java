package com.professeurburp.deezerapitest.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExecutorPool {
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    @Inject
    public ExecutorPool(){
        this(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
    }

    private ExecutorPool(Executor diskIOExecutor, Executor networkIOExecutor, Executor mainThreadExecutor) {
        diskIO = diskIOExecutor;
        networkIO = networkIOExecutor;
        mainThread = mainThreadExecutor;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
                mainThreadHandler.post(command);
        }
    }
}
