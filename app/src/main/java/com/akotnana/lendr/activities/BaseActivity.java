package com.akotnana.lendr.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.akotnana.lendr.utils.FontManager;


/**
 * Created by anees on 5/1/2017.
 */

public class BaseActivity extends AppCompatActivity {

    public static Context applicationContext = null;
    public static Thread.UncaughtExceptionHandler defaultHandler = null;
    public static Thread.UncaughtExceptionHandler exceptionHandler = null;

    public FontManager fontManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(defaultHandler == null){
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        }

        if(applicationContext == null){
            applicationContext = getApplicationContext();
        }

        fontManager = new FontManager(applicationContext);

        if(exceptionHandler == null){
            exceptionHandler = new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                    Log.e("Uncaught Exception", paramThrowable.getMessage());
                    defaultHandler.uncaughtException(paramThread, paramThrowable);

                }
            };

            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
        }
    }
}