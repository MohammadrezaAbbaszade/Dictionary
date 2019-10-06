package com.example.dictionary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dictionary.database.WordDBSchema;
import com.example.dictionary.database.WordOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordRepository {
    private static WordRepository ourInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;


    private WordRepository(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new WordOpenHelper(mContext).getWritableDatabase();
    }

    public static WordRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new WordRepository(context);
        }
        return ourInstance;
    }

    private Cursor queryWord(String where, String[] whereArgs) {
        return mDatabase.query(WordDBSchema.Word.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
    }

    public List<Word> getWords() {
        List<Word> wordList = new ArrayList<>();

        Cursor cursor = queryWord(null, null);

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String strUUID = cursor.getString(cursor.getColumnIndex(WordDBSchema.Word.Cols.UUID));
                String name = cursor.getString(cursor.getColumnIndex(WordDBSchema.Word.Cols.NAME));
                String language = cursor.getString(cursor.getColumnIndex(WordDBSchema.Word.Cols.LANGUAGE));


                Word word = new Word(UUID.fromString(strUUID));
                word.setLanguage(language);
                word.setNAME(name);
                wordList.add(word);

                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }


        return wordList;
    }

    public Word getWord(UUID uuid) {
        String[] whereArgs = new String[]{uuid.toString()};
        Cursor cursor = queryWord(WordDBSchema.Word.Cols.UUID + " = ?", whereArgs);

        try {
            if (cursor == null || cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();

            String strUUID = cursor.getString(cursor.getColumnIndex(WordDBSchema.Word.Cols.UUID));
            String name = cursor.getString(cursor.getColumnIndex(WordDBSchema.Word.Cols.NAME));
            String language = cursor.getString(cursor.getColumnIndex(WordDBSchema.Word.Cols.LANGUAGE));

            Word word = new Word(UUID.fromString(strUUID));
            word.setLanguage(language);
            word.setNAME(name);


            return word;

        } finally {
            cursor.close();
        }
    }

    public void insertWord(Word word) {
        ContentValues values = getContentValues(word);
        mDatabase.insertOrThrow(WordDBSchema.Word.NAME, null, values);
    }

    public void updateWord(Word word) throws Exception {
        ContentValues values = getContentValues(word);
        String where = WordDBSchema.Word.Cols.UUID + " = ?";
        String[] whereArgs = new String[]{word.getUUID().toString()};
        mDatabase.update(WordDBSchema.Word.NAME, values, where, whereArgs);
    }

    public void deleteWord(Word word) throws Exception {
        Word w = getWord(word.getUUID());
        if (w == null)
            throw new Exception("This crime does not exist!!!");
        String where = WordDBSchema.Word.Cols.UUID + " = ?";
        String[] whereArgs = new String[]{word.getUUID().toString()};
        mDatabase.delete(WordDBSchema.Word.NAME, where, whereArgs);
    }

    private ContentValues getContentValues(Word word) {
        ContentValues values = new ContentValues();
        values.put(WordDBSchema.Word.Cols.UUID, word.getUUID().toString());
        values.put(WordDBSchema.Word.Cols.LANGUAGE, word.getLanguage());
        values.put(WordDBSchema.Word.Cols.NAME, word.getNAME());

        return values;
    }
}
