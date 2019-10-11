package com.example.dictionary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.dictionary.database.WordDBApplication;
import com.example.dictionary.database.WordDBSchema;
import com.example.dictionary.database.WordOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordRepository {
    private static WordRepository ourInstance;
    private DaoSession daoSession;
    private WordDao wordDao;


    private WordRepository() {
        daoSession = WordDBApplication.getInstance().getDaoSession();
        wordDao = daoSession.getWordDao();
    }

    public static WordRepository getInstance() {
        if (ourInstance == null) {
            ourInstance = new WordRepository();
        }
        return ourInstance;
    }

    public List<Word> getWords() {
        return wordDao.loadAll();
    }

    public Word getWord(Long uuid) {
        return wordDao.queryBuilder()
                .where(WordDao.Properties.MUUID.eq(uuid)).unique();
    }

    public void insertWord(Word word) {
        wordDao.insert(word);
    }

    public void updateWord(Word word) throws Exception {
        wordDao.update(word);
    }

    public void deleteWord(Word word) throws Exception {
        Word w = getWord(word.getMUUID());
        if (w == null)
            throw new Exception("This crime does not exist!!!");
       wordDao.delete(w);
    }
    public void deleteWords(List<Word> wordList) throws Exception {
        for (Word w : wordList) {
            Word w2 = getWord(w.getMUUID());
            if (w2 == null)
                throw new Exception("You Dont Have Any Tasks  To Delete!!!");
            deleteWord(w2);
        }
    }
}
