package com.todo.app;

import android.app.Application;
import android.content.Context;

public class ApplicationClass extends Application {
    public static Context mcontext;
    @Override
    public void onCreate() {
        super.onCreate();

        mcontext = this;

    }

}
