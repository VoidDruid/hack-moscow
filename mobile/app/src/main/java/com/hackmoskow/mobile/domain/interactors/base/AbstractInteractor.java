package com.hackmoskow.mobile.domain.interactors.base;

import com.hackmoskow.mobile.domain.executor.Executor;
import com.hackmoskow.mobile.domain.threading.MainThread;

public class AbstractInteractor {
    protected Executor threadExecutor;
    protected MainThread mainThread;

    public AbstractInteractor(Executor executor, MainThread mainThread) {
        threadExecutor = executor;
        this.mainThread = mainThread;
    }
}
