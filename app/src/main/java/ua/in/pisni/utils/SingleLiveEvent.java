package ua.in.pisni.utils;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private static final String TAG = "SingleLiveEvent";

    private final AtomicBoolean pending = new AtomicBoolean(false);

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {
        if(hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                if(pending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    @Override
    public void setValue(T value) {
        super.setValue(value);
        pending.set(true);
        super.setValue(value);
    }

    @MainThread
    public void call() {
        setValue(null);
    }

}
