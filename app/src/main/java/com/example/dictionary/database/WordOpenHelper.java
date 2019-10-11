package com.example.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dictionary.model.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class WordOpenHelper extends DaoMaster.DevOpenHelper {

    public WordOpenHelper(Context context, String name) {
        super(context, name);
    }

    public WordOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
