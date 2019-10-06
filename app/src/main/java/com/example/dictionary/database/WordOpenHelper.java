package com.example.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WordOpenHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public WordOpenHelper(@Nullable Context context) {
        super(context, WordDBSchema.NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " +  WordDBSchema.Word.NAME + "(" +
                WordDBSchema.Word.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WordDBSchema.Word.Cols.UUID + "," +
                WordDBSchema.Word.Cols.ENGLISHNAME + "," +
                WordDBSchema.Word.Cols.PERSIANNAME +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
