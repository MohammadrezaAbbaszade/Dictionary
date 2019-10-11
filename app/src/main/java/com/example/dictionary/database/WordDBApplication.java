package com.example.dictionary.database;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.dictionary.model.DaoMaster;
import com.example.dictionary.model.DaoSession;

public class WordDBApplication extends Application {

    private static WordDBApplication application;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        WordOpenHelper daoOpenHelper = new WordOpenHelper(this, "Dictionary.db");
        SQLiteDatabase database = daoOpenHelper.getWritableDatabase();
        daoSession = new DaoMaster(database).newSession();

        application = this;
    }

    public static  WordDBApplication getInstance() {
        return application;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
